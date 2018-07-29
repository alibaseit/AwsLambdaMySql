package com.ozruit.lambda.awslambda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaFunctionHandler implements RequestHandler<Request, String> {
	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;

	@Override
	public String handleRequest(Request request, Context context) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			Employee employee = new Employee();
			employee.setId(request.id);
			employee.setName(request.name);
			session.save(employee);
			session.getTransaction().commit();
		}

		return String.format("Added %s %s.", request.id, request.name);
	}

	private String getCustomer() {
		try {
			// Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection(
					"jdbc:mysql://uuuu.c6oalrfbg07t.llll.rds.amazonaws.com:3306/ozruitdb", "aaaaa",
					"xxxx");
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select name from customer where id = 1 ");

			if (resultSet.next())
				return resultSet.getString("name");
			return "no record";
		} catch (Exception e) {
			e.printStackTrace();
			return "hata: " + e.getMessage();
		} finally {
			close();
		}

	}

	private void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}

	public static void main(String[] args) {
		LambdaFunctionHandler handler = new LambdaFunctionHandler();
		System.out.println(handler.getCustomer());
	}
}
