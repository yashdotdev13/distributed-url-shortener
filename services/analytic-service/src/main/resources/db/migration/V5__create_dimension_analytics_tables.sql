CREATE TABLE browser_analytics (

                                   id BIGSERIAL PRIMARY KEY,

                                   short_code VARCHAR(30) NOT NULL,

                                   browser VARCHAR(50) NOT NULL,

                                   clicks BIGINT NOT NULL DEFAULT 0,

                                   CONSTRAINT uk_browser
                                       UNIQUE(short_code, browser)

);

CREATE TABLE device_analytics (

                                  id BIGSERIAL PRIMARY KEY,

                                  short_code VARCHAR(30) NOT NULL,

                                  device_type VARCHAR(30) NOT NULL,

                                  clicks BIGINT NOT NULL DEFAULT 0,

                                  CONSTRAINT uk_device
                                      UNIQUE(short_code, device_type)

);

CREATE TABLE operating_system_analytics (

                                            id BIGSERIAL PRIMARY KEY,

                                            short_code VARCHAR(30) NOT NULL,

                                            operating_system VARCHAR(50) NOT NULL,

                                            clicks BIGINT NOT NULL DEFAULT 0,

                                            CONSTRAINT uk_operating_system
                                                UNIQUE(short_code, operating_system)

);