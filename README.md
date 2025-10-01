# Reflection
### Reflection is a micro-blogging social platform, created in an ethical way, putting the user first.
This is the server component of reflection.

Before attempting to run this, make sure the following software dependencies are satisfied:
- MariaDB 10.11.11 or newer
- Oracle OpenJDK 24 or newer
- Git 2.50 or newer

To run the server:
- `git clone https://github.com/Kuj0j0taro123/reflection-server.git`
- `cd reflection-server`
- Make sure the database credentials in application.properties is correct
- `./mvnw spring-boot:run`