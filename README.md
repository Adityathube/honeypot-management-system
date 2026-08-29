# 🛡️ Honeypot Management System

A Java-based distributed honeypot system developed as a diploma project to explore network monitoring, TCP socket communication, abnormal traffic detection, decoy-server forwarding, and MySQL-backed event/data storage.

> **Project status:** Diploma project / educational prototype. The repository preserves the original architecture while applying basic cleanup for public sharing.

## 🎯 Overview

The system consists of three Java applications:

1. **Honeypot Client** — selects and transmits dataset records to the honeypot server.
2. **Honeypot Server** — receives records on TCP port `6666`, queues them, stores them, monitors queue activity, and can forward selected records to the decoy server.
3. **Decoy Server** — listens on TCP port `1215` and stores forwarded records in a separate database.

The original project uses a dataset containing dated state/district statistics as the payload for demonstrating traffic and processing behaviour.

## 🏗️ Architecture

```text
                       ┌──────────────────┐
                       │  Honeypot Client │
                       │   Java Swing     │
                       └────────┬─────────┘
                                │
                         TCP / Data Stream
                                │ :6666
                                ▼
                     ┌─────────────────────┐
                     │   Honeypot Server   │
                     │                     │
                     │ DataReceiver        │
                     │ DataQueue           │
                     │ HoneyPortPerformer  │
                     │ Traffic monitoring  │
                     └───────┬─────────────┘
                             │
                 ┌───────────┴───────────┐
                 │                       │
                 ▼                       ▼
          ┌─────────────┐       ┌────────────────┐
          │ Honeypot DB │       │  Decoy Server  │
          │   MySQL     │       │ Java / TCP     │
          └─────────────┘       │     :1215      │
                                └───────┬────────┘
                                        │
                                        ▼
                                ┌─────────────┐
                                │  Decoy DB   │
                                │    MySQL    │
                                └─────────────┘
```

## 🔄 Data Flow

```text
Input dataset
     ↓
Honeypot Client
     ↓ TCP :6666
Honeypot Server
     ↓
In-memory queue
     ├──→ Honeypot MySQL database
     │
     └──→ abnormal/high-traffic condition
              ↓
         Decoy Server :1215
              ↓
         Decoy MySQL database
```

## ⚙️ Main Components

### Honeypot Client

- Java Swing interface
- Reads tabular data
- Converts rows into transferable records
- Sends records to the configured honeypot-server IP
- Uses TCP sockets for communication

### Honeypot Server

Important classes include:

- `DataReceiver` — accepts incoming TCP connections on port `6666` and places received records into the queue.
- `DataQueue` — holds received records for processing.
- `HoneyPortPerformer` — processes queued records, stores them in MySQL, monitors queue behaviour, and controls decoy forwarding.
- `DataSenderDecoy` — forwards selected records to the decoy server.

### Decoy Server

- Listens on TCP port `1215`
- Receives forwarded records
- Stores received records in a separate MySQL database

## 🧪 Traffic / Attack Simulation

The original client contains timing logic that deliberately changes its transmission rate during a portion of the dataset transfer. The honeypot server compares queue-size changes and displays states such as `DOS ATTACK` and `NORMAL AND DEFENCE ACTIVATED`.

This is a **basic educational traffic-detection heuristic**, not a production DDoS detector.

## 🛠️ Technology Stack

- Java
- Java Swing
- TCP/IP socket programming
- MySQL
- JDBC
- JExcelAPI (`jxl`) for spreadsheet input
- NetBeans (original development environment)

## 📁 Repository Structure

```text
honeypot-management-system/
├── README.md
├── .gitignore
├── LICENSE
├── database/
│   ├── honeypot_server.sql
│   └── decoy_server.sql
├── client/
│   └── src/
├── honeypot-server/
│   └── src/
└── decoy-server/
    └── src/
```

## 🚀 Setup

### Prerequisites

- Java JDK
- MySQL Server
- MySQL Connector/J
- JExcelAPI (`jxl`) for the client
- An IDE such as NetBeans or IntelliJ IDEA

### 1. Clone the repository

```bash
git clone https://github.com/Adityathube/honeypot-management-system.git
cd honeypot-management-system
```

### 2. Create the databases

Import the SQL files from `database/`. The source project uses separate databases for the honeypot server and decoy server.

### 3. Configure database environment variables

The public repository intentionally does **not** contain database passwords. Configure these variables locally:

```text
HONEYPOT_CLIENT_DB_URL=jdbc:mysql://localhost:3306/honeypot_server_db
HONEYPOT_SERVER_DB_URL=jdbc:mysql://localhost:3306/hp_server
DECOY_SERVER_DB_URL=jdbc:mysql://localhost:3306/decoy_server_db
HONEYPOT_DB_USER=your_mysql_username
HONEYPOT_DB_PASSWORD=your_mysql_password
```

The exact client database is only needed if you use the client's local database functionality.

### 4. Add required libraries

The original NetBeans project referenced:

- MySQL Connector/J
- JExcelAPI (`jxl.jar`)

Add these libraries to the relevant project classpaths in your IDE.

### 5. Start the applications

Run the applications in this order:

```text
1. Decoy Server
2. Honeypot Server
3. Honeypot Client
```

The honeypot server accepts client data on TCP port `6666`. The decoy server listens on TCP port `1215`.

### 6. Connect the components

- Start the decoy server and note its IP address.
- Start the honeypot server and enter the decoy server IP.
- Start the client and enter the honeypot server IP.
- Select the appropriate input dataset and begin transmission.

## 🔐 Security Notes

This repository has been cleaned to avoid publishing local machine paths and hard-coded database credentials.

The honeypot should only be operated in a controlled lab or other environment where you have authorization to monitor the traffic. Do not expose the diploma prototype directly to the public Internet without appropriate isolation and hardening.

## 📈 Limitations

This is an educational prototype. The original implementation has limitations including:

- Basic queue-size heuristics for attack detection
- No authentication between components
- No TLS encryption
- Limited connection/session logging
- Hard-coded network ports in the original implementation
- Legacy GUI and dependency choices
- Limited automated tests

## 🚀 Future Improvements

- Real-time security dashboard
- Configurable detection thresholds
- Structured security-event logging
- IP/session tracking
- Authentication and encrypted communication
- Better anomaly-detection algorithms
- Docker-based deployment
- Automated tests
- Modern dependency management
- Machine-learning-assisted traffic classification

## 🧠 Learning Outcomes

This project provided practical experience with:

- Client-server architecture
- TCP socket programming
- Java multithreading
- Queue-based processing
- JDBC and MySQL
- Java Swing
- Basic traffic monitoring
- Honeypot and decoy concepts
- Database-backed application design

## 👨‍💻 Project Background

Originally developed as a diploma project and later cleaned and documented for publication as part of a software-development and cybersecurity portfolio.

## ⚠️ Disclaimer

This project is intended for educational and authorized cybersecurity research. Use it only on systems and networks you own or are explicitly authorized to test.
