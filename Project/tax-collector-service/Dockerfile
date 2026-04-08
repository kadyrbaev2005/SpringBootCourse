# Этап 1: Сборка (Maven + JDK 17)
FROM maven:3.8.5-eclipse-temurin-17 AS build
WORKDIR /app

# Копируем настройки и зависимости
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Копируем исходный код и собираем JAR
COPY src ./src
RUN mvn clean package -DskipTests

# Этап 2: Запуск (Легковесный JRE)
FROM eclipse-temurin:17-jre
WORKDIR /app

# Создаем пользователя для безопасности (на базе Ubuntu/Debian команда useradd)
RUN useradd -ms /bin/bash spring
USER spring

# Копируем результат сборки
COPY --from=build /app/target/*.jar app.jar

# Основной порт приложения
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]