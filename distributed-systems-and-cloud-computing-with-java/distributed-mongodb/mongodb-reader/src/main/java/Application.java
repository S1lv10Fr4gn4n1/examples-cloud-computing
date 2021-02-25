import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;

public class Application {

    private static final String MONGO_DB_URL = "mongodb://127.0.0.1:27017,127.0.0.1:27018,127.0.0.1:27019/?replicaSet=rs0";
    private static final String DB_NAME = "online-school";
    private static final double MIN_GPA = 90.0;

    public static void main(String[] args) {
        Application application = new Application();

        Student student = application.parseArgs(args);
        MongoDatabase mongoDatabase = application.connectToMongoDB();

        application.enroll(mongoDatabase, student);
    }

    private Student parseArgs(String[] args) {
        String courseName = "";
        if (args.length >= 1) {
            courseName = args[0];
        } else {
            throw new RuntimeException("Missing course's name");
        }

        String studentName = "";
        if (args.length >= 2) {
            studentName = args[1];
        } else {
            throw new RuntimeException("Missing student's name");
        }

        int age = -1;
        if (args.length >= 3) {
            age = Integer.parseInt(args[2]);
        } else {
            throw new RuntimeException("Missing student's age");
        }

        int gpa = -1;
        if (args.length >= 4) {
            gpa = Integer.parseInt(args[3]);
        } else {
            throw new RuntimeException("Missing student's gpa");
        }

        return new Student(studentName, courseName, age, gpa);
    }

    private MongoDatabase connectToMongoDB() {
        String mongoDbUrl = getMongoDbUrl();
        MongoClient mongoClient = new MongoClient(new MongoClientURI(mongoDbUrl));
        return mongoClient.getDatabase(DB_NAME);
    }

    private String getMongoDbUrl() {
        String bootstrapServers = System.getenv("MONGO_DB_URL");
        return Objects.requireNonNullElse(bootstrapServers, MONGO_DB_URL);
    }

    private void enroll(MongoDatabase database, Student student) {
        if (!isValidCourse(database, student.course)) {
            System.out.println("Invalid course " + student.course);
            return;
        }

        MongoCollection<Document> courseCollection = getCourseCollection(database, student.course);
        if (isStudentEnrolled(courseCollection, student.name)) {
            System.out.printf("Student %s already enrolled\n", student.name);
            return;
        }

        if (student.gpa < MIN_GPA) {
            System.out.println("Please improve your grades");
            return;
        }

        addUserToCourse(courseCollection, student);
        System.out.printf("Student %s was successfully enrolled in %s\n", student.name, student.course);

        printCourseCollection(courseCollection);
    }

    private boolean isValidCourse(MongoDatabase database, String course) {
        for (String collectionName : database.listCollectionNames()) {
            if (collectionName.equalsIgnoreCase(course)) {
                return true;
            }
        }
        return false;
    }

    private MongoCollection<Document> getCourseCollection(MongoDatabase database, String course) {
        return database
                .getCollection(course)
                .withWriteConcern(WriteConcern.MAJORITY)
                .withReadPreference(ReadPreference.primaryPreferred());
    }

    private boolean isStudentEnrolled(MongoCollection<Document> courseCollection, String name) {
        return courseCollection.find(eq("name", name)).first() != null;
    }

    private void addUserToCourse(MongoCollection<Document> courseCollection, Student student) {
        Document document = new Document("name", student.name)
                .append("age", student.age)
                .append("gpa", student.gpa);
        courseCollection.insertOne(document);
    }

    private void printCourseCollection(MongoCollection<Document> courseCollection) {
        for (Document document : courseCollection.find()) {
            System.out.println(document);
        }
    }

    private static class Student {

        private final String name;
        private final String course;
        private final int age;
        private final double gpa;

        public Student(String name, String course, int age, double gpa) {
            this.name = name;
            this.course = course;
            this.age = age;
            this.gpa = gpa;
        }

        public String getName() {
            return name;
        }

        public String getCourse() {
            return course;
        }

        public int getAge() {
            return age;
        }

        public double getGpa() {
            return gpa;
        }
    }
}
