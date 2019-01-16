#! /bin/bash
source /etc/profile
keytool -genkey -noprompt \
    -alias gateway-identity-jks \
    -dname "CN=Unknown, OU=Unknown, O=Unknown, L=Unknown, ST=Unknown, C=Unknown" \
    -keystore gateway.jks \
    -keyalg RSA \
    -validity 360 \
    -storepass 123456 \
    -keypass 123456

keytool -importkeystore -noprompt \
    -srckeystore gateway.jks \
    -destkeystore gateway.p12 \
    -srcalias gateway-identity-jks \
    -destalias gateway-identity-p12 \
    -srcstoretype jks \
    -deststoretype pkcs12 \
    -srcstorepass 123456 \
    -deststorepass 123456 \
    -srckeypass 123456 \
    -destkeypass  123456


openssl pkcs12 \
    -in gateway.p12 \
    -out gateway_passphrase.pem \
    -passin pass:123456 \
    -password pass:123456 \
    -passout pass:123456


#openssl rsa -in gateway_passphrase.pem -out gateway.pem
openssl x509 \
    -passin pass:123456 \
    -in gateway_passphrase.pem \
    -out cert.pem