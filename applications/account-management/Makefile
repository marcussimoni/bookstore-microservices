build-app:
	mvn clean install -DskipTests

build-image: build-app
	docker build -t bookstore/account-management .
