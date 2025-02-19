# Étape 1 : Utiliser l'image officielle OpenJDK 8 comme base
FROM openjdk:8-jdk-alpine

# Étape 2 : Définir un répertoire de travail dans le conteneur
WORKDIR /app

# Étape 3 : Copier le fichier JAR généré par Maven/Gradle dans le conteneur
COPY target/apigateway-0.0.1-SNAPSHOT.jar apigateway.jar

# Étape 4 : Exposer le port sur lequel l’application tourne (défini dans application.yml)
EXPOSE 8010

# Étape 5 : Commande pour exécuter l’application
ENTRYPOINT ["java", "-jar", "apigateway.jar"]


# Faire les commandes suivantes :
# mvn clean package
# docker build -t apigateway .
# docker run -p 8010:8010 apigateway