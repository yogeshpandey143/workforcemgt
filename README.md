# workforcemgt   

**WorkForcemgmt – Workforce Task Management API**
A Spring Boot REST API for managing operational tasks, enabling managers and employees to create, update, and track task progress with activity history, priorities, and assignee-based filters.
📁 Folder Structure

workforcemgmt/
├── controller/                  # REST controllers
│   └── TaskManagementController.java
├── dto/                        # DTOs for requests/responses
│   └── TaskCreateRequest.java
│   └── UpdateTaskRequest.java
│   └── TaskDetailsDto.java
├── exception/                  # Custom exceptions
│   └── ResourceNotFoundException.java
├── mapper/                     # Model <-> DTO converters
│   └── ITaskManagementMapper.java
├── model/                      # Domain models
│   └── TaskManagement.java
│   └── TaskActivity.java
│   └── TaskComment.java
├── model/enums/               # Enum definitions
│   └── Task.java
│   └── TaskStatus.java
│   └── TaskPriority.java
│   └── ReferenceType.java
├── repository/                # Spring Data repositories
│   └── TaskRepository.java
│   └── TaskActivityRepository.java
│   └── TaskCommentRepository.java
├── service/                   # Interfaces for services
│   └── TaskManagementService.java
├── service/impl/              # Business logic implementations
│   └── TaskManagementServiceImpl.java
├── WorkforcemgmtApplication.java
└── resources/
    └── application.properties

🚀 Getting Started
Prerequisites:
- Java 17
- Gradle
- Spring Boot 3.x

Setup Instructions:

# Clone the repository
git clone  https://github.com/yogeshpandey143/workforcemgt.git

# Navigate into the project directory
cd workforcemgt

# Build and run
./gradlew bootRun

📌 API Endpoints
✅ Create Tasks
POST /task-mgmt/create

{
  "requests": [
    {
      "reference_id": 3001,
      "reference_type": "ORDER",
      "task": "CREATE_INVOICE",
      "assignee_id": 9010,
      "priority": "HIGH",
      "task_deadline_time": 1726000000000
    }
  ]
}

✏️ Update Tasks
POST /task-mgmt/update

{
  "requests": [
    {
      "task_id": 1,
      "task_status": "CANCELLED",
      "description": "Cancelled by user request"
    }
  ]
}

🔍 Get Task By ID
GET /task-mgmt/{id}
Example: GET /task-mgmt/5
📅 Fetch Tasks By Date & Assignee
POST /task-mgmt/fetch-by-date/v2

{
  "assignee_ids": [9001, 9002],
  "start_date": 1725465600000,
  "end_date": 1725907200000
}

🚦 Update Task Priority
PATCH /task-mgmt/update-priority/{taskId}?priority=HIGH
Examples:
- PATCH /task-mgmt/update-priority/1?priority=HIGH
- PATCH /task-mgmt/update-priority/5?priority=LOW
📌 Enums Used
TaskPriority: LOW, MEDIUM, HIGH
TaskStatus: ASSIGNED, STARTED, COMPLETED, CANCELLED
Task: CREATE_INVOICE, ARRANGE_PICKUP, COLLECT_PAYMENT, ASSIGN_CUSTOMER_TO_SALES_PERSON
ReferenceType: ORDER, ENTITY, ENQUIRY
✅ Notes
Timestamps (e.g. task_deadline_time, created_at) must be in Epoch milliseconds.
All tasks include logging to TaskActivity and support threaded comments via TaskComment.
👨‍💻 Author
Yogesh Pandey
GitHub: https://github.com/yogeshpandey143
