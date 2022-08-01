build-app:
	mvn clean install

build-image: build-app
	docker build -t bookstore/bookstore-catalog .
