<h3>SETTING UP SIGNUP SERVICES</h3>
1) Goto <code>KafkaProducerConfiguration class</code> change the <code>BOOTSTRAP_SERVER_CONFIG</code> to the <code>IP</code> and <code>Port</code> of the host machine where your Kafka server is running.

2) Run the command <code>mvn spring-boot:build-image</code> to build a docker image of the signup service.


3) Run the command <code>docker run -it -p8080:8080 signup:0.0.1-SNAPSHOT</code> to run the built docker image. This will map the service to port <code>8080</code> on your local machine/server.


4) Make a post rest call to <code>http://<HOST/SERVER_IP>:8080/signup/api/v1/signup</code> with your request body looking like this <code> 
{ "email": "testemail@overonix.com", "password": "reallyStrongPassword" }</code> NOTE: The <code>HOST/SERVER_IP</code> will be the <code>IP</code> of your local machine/box in this case.
   
Kindly reach out to if you encounter any issue running this service. <b>Email: </b><code>akobundumichael94@gmail.com</code>
