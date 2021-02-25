
#!/bin/bash

# Generate key pair files

keytool -genkeypair -alias apiEncryptionKey -keyalg RSA \
-dname "CN=Silvio F.S.,OU=API Development,O=silvio.tech,L=Berlin,C=DE" \
-keypass q1w2e3r4 -keystore apiEncryptionKey.jks -storepass q1w2e3r4


keytool -importkeystore -srckeystore apiEncryptionKey.jks -destkeystore apiEncryptionKey.jks -deststoretype pkcs12