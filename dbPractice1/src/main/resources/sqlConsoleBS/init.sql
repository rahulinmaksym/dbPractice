CREATE TABLE IF NOT EXISTS homeworks (
    id serial,
    name VARCHAR ( 50 ) UNIQUE NOT NULL,
    description VARCHAR ( 1024 ) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS lessons (
    id serial,
    name VARCHAR ( 50 ) UNIQUE NOT NULL,
    updated_at date NOT NULL DEFAULT CURRENT_DATE,
    homework_id INT NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT fk_homework FOREIGN KEY(homework_id) REFERENCES homeworks(id)
);

CREATE TABLE IF NOT EXISTS schedules (
    id serial,
    name VARCHAR ( 50 ) UNIQUE NOT NULL,
    updated_at date NOT NULL DEFAULT CURRENT_DATE,
    PRIMARY KEY(id)
);

CREATE TABLE schedule_lesson (
    schedule_id INT NOT NULL,
    lesson_id INT NOT NULL,
    PRIMARY KEY (schedule_id, lesson_id),
    CONSTRAINT fk_schedule FOREIGN KEY(schedule_id) REFERENCES schedules(id),
    CONSTRAINT fk_lesson FOREIGN KEY(lesson_id) REFERENCES lessons(id)
);
