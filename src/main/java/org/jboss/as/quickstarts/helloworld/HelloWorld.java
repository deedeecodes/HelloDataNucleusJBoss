/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.helloworld;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.inject.Named;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.Transient;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple CDI service which is able to say hello to someone
 *
 * @author Pete Muir
 *
 */

@Entity
@Named
public class HelloWorld {

private static final Logger logger = LoggerFactory.getLogger(HelloWorld.class);

//@Resource
//@Transient
//UserTransaction tx;

@Id
int messageId;

@Column(name="hellomessage")
String helloMessage;

public HelloWorld(int messageId, String helloMessage) {
super();
this.messageId = messageId;
this.helloMessage = helloMessage;
}

public HelloWorld() {
}

public String getHelloMessage() {
return helloMessage;
}

public void setHelloMessage(String helloMessage) {
this.helloMessage = helloMessage;
}

@Override
public String toString() {
return "HelloWorld [messageId=" + messageId + ", helloMessage=" + helloMessage + "]";
}

public void selectAll() throws InterruptedException, SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException, NotSupportedException {
//@Resource(mappedName="java:/myEntityManagerFactory")
EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello-world");
//@PersistenceContext(type = PersistenceContextType.EXTENDED, unitName="hello-world")
EntityManager em = emf.createEntityManager();
try {
populateDb(em);
while (true) {
Query q2 = em.createQuery("SELECT hw FROM HelloWorld hw");
List result = q2.getResultList();
logger.info(result.toString());
System.out.println(result.toString());
Thread.sleep(500);
}
} finally {
em.close();
}
}

public String selectAllReturnString()  throws SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException, NotSupportedException {
//@Resource(mappedName="java:/myEntityManagerFactory")
EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello-world");
//@PersistenceContext(type = PersistenceContextType.EXTENDED, unitName="hello-world")
EntityManager em = emf.createEntityManager();
try {
populateDb(em);
Query q2 = em.createQuery("SELECT hw FROM HelloWorld hw");
List result = q2.getResultList();
System.out.println(result.toString());
return "Result" + result.toString();
} finally {
em.close();
}
}

public void populateDb(EntityManager em) throws SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException, NotSupportedException {
//EntityTransaction tx = em.getTransaction();
//tx.begin();
HelloWorld hw1 = new HelloWorld(1, "Hello");
HelloWorld hw2 = new HelloWorld(2, "Bonjour");
em.persist(hw1);
em.persist(hw2);
//tx.commit();
}
}
