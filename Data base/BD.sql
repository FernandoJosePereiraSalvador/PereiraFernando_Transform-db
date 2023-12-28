-- Crear la base de datos si no existe
DROP DATABASE IF EXISTS NASA_DB;
CREATE DATABASE IF NOT EXISTS NASA_DB;

-- Seleccionar la base de datos
USE NASA_DB;

CREATE TABLE Mision (
    ID_Mision INT PRIMARY KEY,
    Nombre_Mision VARCHAR(255),
    Fecha_Lanzamiento DATE,
    Fecha_Finalizacion DATE
);

CREATE TABLE Equipo (
    ID_Equipo INT PRIMARY KEY,
    Nombre_Equipo VARCHAR(255),
    ID_Mision INT,
    FOREIGN KEY (ID_Mision) REFERENCES Mision(ID_Mision)
);

CREATE TABLE Cargo (
    ID_Cargo INT PRIMARY KEY,
    Nombre_Cargo VARCHAR(50) UNIQUE
);

CREATE TABLE Astronauta (
    ID_Astronauta INT PRIMARY KEY,
    Nombre VARCHAR(255),
    Apellidos VARCHAR(255),
    Fecha_Nacimiento DATE,
    Fecha_Ingreso_NASA DATE,
    Sexo VARCHAR(10),
    Nacionalidad VARCHAR(50),
    Num_Vuelos_Espaciales INT,
    Horas_Espacio DECIMAL(10, 2),
    ID_Equipo INT,
    ID_Cargo INT,
    FOREIGN KEY (ID_Equipo) REFERENCES Equipo(ID_Equipo),
    FOREIGN KEY (ID_Cargo) REFERENCES Cargo(ID_Cargo)
);

CREATE TABLE Habilidad (
    ID_Habilidad INT PRIMARY KEY,
    Nombre_Habilidad VARCHAR(255)
);

CREATE TABLE Astronauta_Habilidad (
    ID_Astronauta INT,
    ID_Habilidad INT,
    PRIMARY KEY (ID_Astronauta, ID_Habilidad),
    FOREIGN KEY (ID_Astronauta) REFERENCES Astronauta(ID_Astronauta),
    FOREIGN KEY (ID_Habilidad) REFERENCES Habilidad(ID_Habilidad)
);


CREATE TABLE Base (
    ID_Base INT PRIMARY KEY,
    localizacion VARCHAR(255),
    Nombre_Base VARCHAR(255)
);

CREATE TABLE Nave (
    ID_Nave INT PRIMARY KEY,
    Nombre_Nave VARCHAR(255),
    ID_Base INT,
    FOREIGN KEY (ID_Base) REFERENCES Base(ID_Base)
);

CREATE TABLE Nave_Equipo (
    ID_Nave INT,
    ID_Equipo INT,
    PRIMARY KEY (ID_Nave, ID_Equipo),
    FOREIGN KEY (ID_Nave) REFERENCES Nave(ID_Nave),
    FOREIGN KEY (ID_Equipo) REFERENCES Equipo(ID_Equipo)
);

INSERT INTO Cargo (ID_Cargo, Nombre_Cargo) VALUES
(1, 'Teniente'),
(2, 'Ingeniero');

INSERT INTO Base (ID_Base, localizacion, Nombre_Base) VALUES
(1, 'Houston', 'Base Houston'),
(2, 'Cabo Cañaveral', 'Base Cabo Cañaveral');

INSERT INTO Nave (ID_Nave, Nombre_Nave, ID_Base) VALUES
(1, 'Nave Alpha', 1),
(2, 'Nave Beta', 2);

INSERT INTO Mision (ID_Mision, Nombre_Mision, Fecha_Lanzamiento, Fecha_Finalizacion) VALUES
(1, 'Misión A', '2023-01-01', '2023-02-01'),
(2, 'Misión B', '2023-03-01', '2023-04-01');

INSERT INTO Equipo (ID_Equipo, Nombre_Equipo, ID_Mision) VALUES
(1, 'Equipo A', 1),
(2, 'Equipo B', 2);

INSERT INTO Astronauta (ID_Astronauta, Nombre, Apellidos, Fecha_Nacimiento, Fecha_Ingreso_NASA, Sexo, Nacionalidad, Num_Vuelos_Espaciales, Horas_Espacio, ID_Equipo, ID_Cargo) VALUES
(1, 'John', 'Doe', '1980-01-01', '2000-01-01', 'M', 'USA', 3, 100.5, 1, 1),
(2, 'Jane', 'Smith', '1985-02-15', '2005-05-10', 'F', 'USA', 2, 75.2, 2, 2);

INSERT INTO Habilidad (ID_Habilidad, Nombre_Habilidad) VALUES
(1, 'Piloto'),
(2, 'Ingeniería');

INSERT INTO Astronauta_Habilidad (ID_Astronauta, ID_Habilidad) VALUES
(1, 1),
(2, 2);

INSERT INTO Nave_Equipo (ID_Nave, ID_Equipo) VALUES
(1, 1),
(2, 2);


