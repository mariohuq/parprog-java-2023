---
title: Вводная лабораторная работа по параллельному программированию
---

См. [решение возникших проблем с Maven](maven-errs.md).

# Задание

1. Создать Java-проект с помощью Maven.
2. Реализовать функцию `primeFactors`, которая раскладывает число на простые множители.
3. Написать unit-тесты для этой функции.

# Установка требуемых инструментов

Я использую Fedora, в ней установка Git и Maven производится командой:

```shell
# dnf install git maven
```

Для установки Java Development Kit я воспользовался утилитой [`sdkman`](https://sdkman.io/):

```shell
$ sdk install java 21-ms
```

Эта команда устанавливает OpenJDK от Microsoft версии 21.

# Создание проекта

Шаблон проекта создается командой `mvn archetype:generate`:

```shell
$ mvn archetype:generate \
    -DgroupId=ru.spbstu.telematics.java \
    -DartifactId=prime-factorizator \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DinteractiveMode=false
``` 

# Метод решения

В предложенной библиотеке `org.apache.commons.math3` уже реализована функция [primeFactors](https://commons.apache.org/proper/commons-math/javadocs/api-3.6.1/org/apache/commons/math3/primes/Primes.html#primeFactors%28int%29), решающая поставленную задачу.
Однако она бросает исключение при передаче ей отрицательных чисел или чисел, не имеющих простых делителей (0 и ±1).

Моя функция использует библиотечную. Она обрабатывает описанные случаи как штатные, возвращая список простых делителей и для отрицательных чисел и для 0 или 1:

```java
public static List<Integer> primeFactors(int n) {
    if (n == Integer.MIN_VALUE) {
        var result = primeFactors(n / 2);
        result.addFirst(2);
        return result;
    }
    if (n < 0) {
        return primeFactors(-n);
    }
    if (n < 2) {
        return Collections.emptyList();
    }
    return Primes.primeFactors(n);
}
```

# Юнит-тесты

Я проверял случаи:

- $n < 0$
- $n = 0, n = ±1$
- числа с повторяющимися простыми делителями
- $n = 720720$ (имеет 10 простых делителей) 
- экстремальные случаи: $n = -2^{31}$, $n = 2^{31} - 1$

Тесты расположены в `AppTest.java`.

# Запуск тестов

Версия Maven и Java:

```shell
$ mvn -v
Apache Maven 3.9.2 (c9616018c7a021c1c39be70fb2843d6f5f9b8a1c)
Maven home: /..../maven/lib/maven3
Java version: 21, vendor: Microsoft, runtime: /..../java/21-ms
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "6.5.6-200.fc38.x86_64", arch: "amd64", family: "unix"
```

Запуск тестов:

```shell
$ cd prime-factorizator
$ mvn test
[INFO] ------------< ru.spbstu.telematics.java:prime-factorizator >------------
[INFO] Building prime-factorizator 1.0-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running ru.spbstu.telematics.java.AppTest
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.117 s - in ru.spbstu.telematics.java.AppTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  3.334 s
[INFO] Finished at: 2023-10-18T21:56:55+03:00
[INFO] ------------------------------------------------------------------------
```