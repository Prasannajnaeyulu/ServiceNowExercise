# ServiceNowExercise
Automated ServiceNow Test application Gurukula through selenium with Java

/**
   @author - Prasannanjaneyulu Padavala
   Date: 14/05/2016, Saturday
**/

Framework:
=========
1. I followed Data Drivern Framework with Page Object Model. Ideally, Data has to be read from external sources like excel. I had written the common code to read the data from xls as well. I just need to create some xls data files and use the data in it in my test cases. Instead, I just hard coded the data.

2. I created TestNG customized listerner to capture the screenshot whenever a test fails. Rightnow, screenshots are capturing in projectdir/Screenshot folder with testnameandtimestamp as filename. Also, thought of capturing verification failures in a test in to a map. Whenever any test finishes execution, i wanna look into this map in the listener claCovess to see whether it's empty or it has got any failures. If it's not empty, would like to fail the test. I had written the required code in the listener but didn't see a provision as there is no need to continue the test further if any such failures happens in the test cases. So, I just used assertions wherever we need a check. So that, test would fail instantly if there is any assertion failure.

3. All pages got one common parent AbstractPageObject where i defined the code to create a driver instance and opening browser and killing the browser etc.., synchronized common actions that can perform on a webpage like, click, type etc..

4. I Created this as a Maven project and also using log4j for logging into file and console. 

Following is my environment...

C:\ServiceNowWorkSpace\ServiceNowGurukula>mvn -version
Apache Maven 3.3.9
Default locale: en_IN, platform encoding: Cp1252
OS name: "windows 8.1", arch: "x86", family: "windows"

Selenium Version:
================
2.53

I Tested only in Firefox browser..

Mozilla Firefox Browser Version:
===============================
40.0

 
Tests Covered:
=============
I Covered mostly the positive test cases which are 16 in total.
Tests Comprises of...
1. CRUD operations on Entities of Branch, Staff.
2. Accounts test cases for finding the session, invalidating all the sessions matches with the criteria set in sessions object, Changing password and Settings etc...
3. Registering a new user, login, invalid login etc..

Not Covered:
1. Negative Test cases like checking boundary values of text field, special characters etc.. are not covered.
2. Pagination related cases are also not covered.

How to Run Tests:
================
Everything is configured in maven pom.xml. I used, maven-compiler-plugin for compiling code and maven-surefire-plugin for running the tests.

Define url, browser information in environment.properties

I defined a sample testng.xml file which was configured in pom.xml which would run by surefire plugin.

So, We just need to run the following command to compile and run. Copy the project into your local machine say..... C:\ServiceNowWorkSpace\ServiceNowGurukula then open command prompt and switch to this path.
whi
C:\ServiceNowWorkSpace\ServiceNowGurukula>mvn compile test

Ways to find out failure reasons:
=================================
1. We are capturing the screenshots whenever a test fails. Which could help us in identifying the issue.
2. We are logging the logs into a test file through log4j which could help us in identifying the code paths where it was failed.
3. TestNG is generating a report once all the tests are finished execution; Which could help us in finding out code flow and point of failure through Stack Trace.

If nothing from above worked. We always have a better way; Debugging the code from eclipse by keeping break points.

Note:
=====
1. While Running the tests please change the test data in *Tests.java classes as per your application environment.
2. To Import the project in eclipse, run mvn eclipse:eclipse target first and then import it in eclipse.

Excuses:(Due to application limitations)
========

1. Registering new user is getting failed. All the time, It's saying Registration Failed. Please Try again later. I tried with different sets and combinations of data but no luck.

2. Delete functionality of Branch is not working in case this branch is referring in an existing Staff. Delete button is enabled but it's not producing error message in case the branch is failed to delete.

3. ChangePassword will fail as i am giving the old and new password same. I didn't try the succeess case. Because, changing password might affect another test cases.
   But, I wanna give a try by creating a new user. But, new user creation is getting failed all the time.

Excuses: (I failed to fix. Due to running out of time i didn't fix them)
=======

1. I am running each and every test independently. So, closing the browser and restarting again in test case. 
 Of course, It's creating many Sessions in the sessions table. Which can be managed by logging out once each test is done. 
 So, I just left it to check multiple session invalidate feature. 

 We can better manage the tests execution like running each class independently rather than each test to reduce the burden.

2. Using different data sets and passing them through external data source like excel instead of hardcoding in tests.

3. Not cleaning the Screenshots folder upon each cycle of run. Hence, old run files are also been part of Screenshots folder.
