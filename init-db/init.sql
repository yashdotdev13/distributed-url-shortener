CREATE DATABASE url_auth_db;
CREATE DATABASE url_service_db;
CREATE DATABASE analytics_service_db;

GRANT ALL PRIVILEGES ON DATABASE url_auth_db TO postgres;
GRANT ALL PRIVILEGES ON DATABASE url_service_db TO postgres;
GRANT ALL PRIVILEGES ON DATABASE analytics_service_db TO postgres;