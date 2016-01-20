all:
	javac -d lib -cp lib src/Battle_Field.java
	javac -d lib -cp lib src/CardWars.java
clean:
	rm *.class
run:
	java -cp lib Battle_Field
	java -cp lib CardWars