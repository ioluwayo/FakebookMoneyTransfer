This command line application uses a demo rest api to simulate a crossborder money trasfer application.

The following operations can be perfomed:
1. Add recipients.
2. Send them money.
3. Check if a payment was sucessfull.

Tools used:
1. Jersy for JSON parsing. inbound and outbound.
2. JUnit for testing
3. Maven.
4. Apache commmons CLI for command line parsing.
5. Spring boot for packaging dependencies into a single executable jar.

Executable jar can be run from command line with the following command.

java -jar FakebookMoneyTransfer.jar

To run the application with a different username and apikey, pass them as arguments through command line. You will need to get an api key from CoolPay

java -jar FakebookMoneyTransfer.jar <username> <apikey> 