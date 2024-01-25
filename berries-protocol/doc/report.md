# Создание многопоточного приложения 1

Ссылка на проект: https://github.com/mariohuq/parprog-java-2023/blob/main/berries-protocol

<!--
Отчет должен содержать задание, методы решения, листинги выполненных команд и написанного кода (нужно приводить не весь код (он в git!), а наиболее значимые его части).
В отчете должна быть приведена ссылка на репозиторий git, где размещен код задания.
Отчет должен быть оформлен в wiki (форматирование http://www.redmine.org/projects/redmine/wiki/RedmineTextFormattingTextile)
-->

## Задание

В данной работе необходимо написать многопоточное приложение, эмулирующее заданную модель. Нужно спроектировать потоки, которые отвечают за поведение сущностей из полученного задания. Взаимодействие потоков должно быть синхронизировано и приложение должно быть протестировано на наличие deadlocks и race conditions. Приложение не должно переставать работать из-за изменения задержек и модель не должна быть полностью синхронной.

## Модель

_Ягоды в саду_. Два враждующих соседа разделены полем с ягодами.
Они разрешили друг другу выходить на поле и собирать ягоды,
но им нужно быть уверенным что только один из них находится на поле в каждый момент времени.
После долгих споров они приняли следующий протокол действий:

1. Когда один из соседей хочет пойти на поле, он поднимает флаг.
2. Если он видит флаг своего соседа, он не заходит на поле и спускает свой флаг и пробует снова.
3. Если он не видит флага соседа, он входит на поле и собирает ягоды.
4. Он опускает свой флаг сразу как выйдет с поля.

Смоделировать эту ситуацию.
Имеется два соседа, N1 и N2.
Необходимо обеспечить раздельный доступ к полю в соответствии с протоколом.
Добавить свойство “Progress” для соседей (количество ягод), чтобы контролировать справедливость протокола.

# Методы решения

Класс `Neigbour` представляет соседа. Он наследует `Thread` и 
его метод `run` отражает логику описанного протокола:

```java
@Override
public void run() {
    String name = getName();
    while (!isInterrupted()) {
        try {
            Thread.sleep(random.nextInt(MAX_PAUSE_MS));
        } catch (InterruptedException e) {
            System.err.println(name + " was interrupted between flag raises");
            break;
        }
        flags.raise(name);
        if (flags.isOtherRaised(name)) {
            flags.lower(name);
            continue;
        }
        System.out.println(name + " enters field");
        try {
            berriesCollected += field.harvest();
        } catch (InterruptedException e) {
            System.err.println(name + " was interrupted while harvesting");
            break;
        } finally {
            System.out.println(name + " left field");
            flags.lower(name);
        }
    }
    System.out.println(name + " collected " + berriesCollected + " berries.");
}
```

Класс `BerriesField` представляет разделяемый ресурс --- поле ягод.
Метод `harvest` симулирует сбор случайного числа ягод:
от 0 до 100 ягод (`MAX_BERRIES_COLLECTED_ONCE`), причем на одну ягоду уходит 1 мс (`ONE_BERRY_TIME_MS`).

Класс `Flags` представляет флаги двух соседей, которые
поднимают свои флаги (`raise`), опускают (`lower`) и смотрят на чужой флаг (`isOtherRaised`).
Эти методы объявлены `synchronized`, чтобы не допустить их одновременную работу в разных потоках.

В методе `main` класса `BerriesField` создаются и запускаются два потока с циклами работы обоих соседей.

Программа работает в течении 5 000 мс (`SIMULATION_TIME_MS`), затем завершается прерыванием обоих потоков.

Корректность работы подтверждается многократным запуском: одновременно на поле ягоды собирает только один сосед.
Для проверки я использовал регулярное выражение

```regexp
((A|B) enters field
\2 started harvesting
\2 finished harvesting
\2 left field
)+
```

Если весь вывод программы (кроме последних 4 строк)
удовлетворяет этому регулярному выражению, то программа корректна.

Пример вывода программы:

```
A enters field
A started harvesting
A finished harvesting
A left field
B enters field
B started harvesting
B finished harvesting
B left field
A enters field
A started harvesting
A finished harvesting
A left field
...
...
A was interrupted between flag raises
B was interrupted between flag raises
B collected 1085 berries.
A collected 698 berries.
```

## Deadlocks и Race conditions

В процессе работы программы могут возникать ситуации, когда оба соседа подняли флаг,
в этом случае никто из них не собирает ягоды. Однако это не приводит ни к взаимной блокировке,
ни к race condition, т. к. изменения флагов происходят согласованно (`synchronized` методы класса `Flags`).

## Запуск

Для запуска нужно выполнить команды (из корня проекта)

```shell
mvn compile
mvn exec:java -Dexec.mainClass=ru.spbstu.telematics.java.BerriesField
```