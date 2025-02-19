# Étape 1 : Utiliser l'image officielle OpenJDK 8 comme base
FROM openjdk:8-jdk-alpine

# Étape 2 : Définir un répertoire de travail dans le conteneur
WORKDIR /app

# Étape 3 : Copier le fichier JAR généré par Maven/Gradle dans le conteneur
COPY target/eureka-0.0.1-SNAPSHOT.jar eureka.jar

# Étape 4 : Exposer le port sur lequel l'application tourne (défini dans application.yml)
EXPOSE 8761

# Étape 5 : Commande pour exécuter l'application
ENTRYPOINT ["java", "-jar", "eureka.jar"]

# Commande pour exécuter l'application
# mvn clean package
# docker build -t eureka .
# docker run -p 8761:8761 eureka