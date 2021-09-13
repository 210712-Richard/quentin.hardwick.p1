# Tuition Reimbursement Management System

## Project Description
The Tuition Reimbursement management System (TRMS) allows employees who attend educational classes,
licensing courses, seminars, and other educational events to submit forms for up to $1000 of reimbursement
per year to offset some or all of the costs of attending. The form is submitted to an employee's direct supervisor
for approval, and thereafter needs to be approved by the head of their department. If both their direct supervisor
and their department head approve of the reimbursement request, it then goes to the Benefits Coordinator (BenCo)
for final approval.

## Technologies Used
* Java
* REST
* Postman
* Javalin
* JUnit
* Log4J
* NoSQL
* AWS S3

## Features
* Users can login
* Users can check their available reimbursement remaining for the year
* Users can submit reimbursement request forms
* Users can view a list of all forms awaiting their approval
* Users can approve any form awaiting their approval
* If the user's direct supervisor is a Department Head, supervisor approval is granted automatically
* If the user is a Department Head, form submission automatically requires BenCo approval only
* Reimbursement amount is calculated automatically based on event type and cost of attendance
* A BenCo approving a reimbursement request deducts the reimbursement amount from the user's available balance
* If requested reimbursement amount is greater than user's remaining balance, user will receive the remainder of their balance only



To-Do List
* Users can send attachments
* Forms to non-BenCos get approved automatically if not approved by set amount of time
* Forms may be denied and sent back to user to dispute
* Users can view history of all past form submissions
* Allow registration of new users

## Getting Started
1. Use Git Bash to download the repository into your local directory using the following command:
`git clone https://github.com/210712-Richard/quentin.hardwick.p1.git`

2. To set up AWS Keyspaces, user will need to store their AWS credentials in the following environment variables:
* AWS_USER
* AWS_PASS

3. Either create an AWS Keyspace with the name `TRMS` or navigate to the util package of the `src/main/java` folder
and enter your own Keyspace name on line 22 of the CassandraUtil class

4. Set up the AWS Trust Store by opening the Bash terminal inside of the `src/main/resources` folder and run the following commands:
* `curl https://certs.secureserver.net/repository/sf-class2-root.crt -O`
* `openssl x509 -outform der -in sf-class2-root.crt -out temp_file.der`
* `keytool -import -alias cassandra -keystore cassandra_truststore.jks -file temp_file.der`
* When prompted, enter the password: `p4ssw0rd`
* Confirm that the password is: `p4ssw0rd`
* Enter `yes` or `y` when asked whether or not to trust the certificate

If successful, you should now have the `cassandra_truststore.jks`, `sf-class2-root.crt`,
and `temp_file.der` files in your `src/main/resources` folder.

5. If you prefer to use default test data, skip to step 6. Otherwise, alter the `DatabaseCreator` class
to use your own custom users.

6. In the Driver class, ensure that the `instantiateDatabase()` method is not commented out and that the
`javalin()` method ***is*** commented. Run the driver to instantiate the tables in your Keyspace. This process
will run for 70 seconds and then the program will terminate.

7. Comment out the `instantiateDatabase()` method to allow for data persistence. Uncomment the `javlin()` method
and then run the Driver class again to load the program.

## Usage

### Log in as a User
* `POST` to `http://localhost:8080/users`
* In the request body, input as JSON: `{"username": "[username]"}`

### Log out as a User
* `DELETE` to `http://localhost:8080/users`

### Check a User's remaining compensation balance
* `GET` to `http://localhost:8080/users/{username}/compensation`

### Submit a Reimbursement Request form
* `POST` to `http://localhost:8080/users/{username}/forms`
* In the request body, input as JSON:
```{
    "name": "[First Last]",
    "date": "[YYYY-MM-DD]",
    "time": "[HH:MM]",
    "location": "[Location event is taking place]",
    "type": "[Type of event]",
    "description": "[Description of event]",
    "justification": "[How event pertains to work]",
    "cost": [Cost of attendance]
}```

1. "name" field shoud contain the first and last name of user submitting the request
2. "date" field shoud contain the day the event is taking place
3. "time" field shoud contain the time of day the event is taking place
4. "location" field shoud contain the place where the event is being held
5. "type" field shoud contain the type of event the employee is seeking reimbursement for. The options are:
    * COURSE
    * SEMINAR
    * CERT_PREP_CLASS
    * CERTIFICATION
    * TRAINING
    * OTHER
6. "description" field shoud contain a brief description of the event
7. "justification" field shoud contain a brief description of how the event attending the event pertains to the company
8. "cost" field shoud contain the raw cost of the event. The actual reimbursement amount awarded is determined by company rules based on the event type

### View all forms awaiting a User's approval
`GET` to `http://localhost:8080/users/{username}/awaitingapproval`
