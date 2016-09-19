# Jaxrs Security

**Jaxrs Security** provides an authorization filter that uses Authorization HTTP header to set JAX-RS API security context. It allows you to use [Javax security annotations](https://docs.oracle.com/javaee/7/api/javax/annotation/security/package-summary.html) in your resources.

This library use [JWT](https://jwt.io/) for securely transmitting information between parties encoded as a JSON object.

You can do a single sign-on (SSO) authentication by choosing the multiple application token type. 

## Get it

Add the following to your Maven configuration:

```xml
<dependency>
	<groupId>com.bekioui.jaxrs</groupId>
	<artifactId>jaxrs-security</artifactId>
	<version>1.1.0</version>
</dependency>
```

## Use it

Import the **JaxrsSecurityConfig** class in  your Spring configuration file:

```java
@Configuration
@Import({ JaxrsSecurityConfig.class })
public class SpringConfig {
  
}
```

## Configure it

You need to define a **secret** to encode and decode tokens on the server. In case you have a multiple application context, you need to define the same **secret** for all your applications. 

### Single application context

Token has an **identifier** that represents the user and a list of **roles** on the application.

```json
{
  "identifier":"solid",
  "roles":["USER"]
}
```

Add the following to your Spring configuration file:

```java
@Bean
public ApplicationTokenDescriptor getTokenDescriptor() {
	return ApplicationTokenDescriptor.getSingleApplicationTokenDescriptor(secret);
}
```

### Multiple application context

Token has an **identifier** that represents the user and a map of **roles** which contains a list of roles for each application defined by its package name.

```json
{
  "identifier":"solid",
  "roles":{
    "com.bekioui.app1":["USER"],
    "com.bekioui.app2":["ADMIN"],
  }
}
```

Each application from your microservices architecture has to add the following to its Spring configuration file:

```java
@Bean
public ApplicationTokenDescriptor getTokenDescriptor() {
	return ApplicationTokenDescriptor.getMultipleApplicationTokenDescriptor("com.bekioui.app", secret);
}
```

Don't forget to use the same **secret** for all your applications.

### Resources

Now you can add, for example, [RolesAllowed](https://docs.oracle.com/javaee/7/api/javax/annotation/security/RolesAllowed.html) annotation to specify which roles are allowed to access to your resource.

```java
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface UserResource {

    @GET
    @RolesAllowed("USER")
    List<String> findAllLogins();
    
}
```

## License
	
	Copyright (C) 2016 Mehdi Bekioui (consulting@bekioui.com)
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
		http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.		