# Работа с коллекциями

Ссылка на проект: https://github.com/mariohuq/parprog-java-2023/blob/main/my-collection

<!--

## Задание

-   Исследовать выбранный тип коллекции, посмотреть абстрактные классы и интерфейсы, которые данный тип реализует.
-   Реализовать интерфейс одной из стандартных или apache коллекции Java (+ добавить параметры типа, + реализовать интерфейс Iterable<T>)
    -   size
    -   contains
    -   add (put для map по ключу)
    -   remove (для map по ключу)
    -   get (для map по ключу)
    -   и другие, применимые к конкретному варианту
-   Использование основных коллекций Java (из core или commons-collections). Написать тест, который демонстрирует аналогичное поведение коллекции java и реализованной вами

## Варианты

1.  List
    1.  ArrayList
    2.  LinkedList
    3.  Bag
2.  Set
    1.  HashSet
    2.  LinkedHashSet
    3.  TreeSet
    4.  HashMultiSet
3.  Map
    1.  HashMap
    2.  LinkedHashMap
    3.  TreeMap
    4.  BidirectionalHashMap
    5.  ArrayListValuedHashMap
    6.  HashSetValuedHashMap
    7.  _MultiKey_
4.  Queue/Stack
    1.  Stack
    2.  Queue/dequeue

## Требования к сдаче

1.  Рабочий код
2.  Ссылка на Github
3.  Unit тесты
4.  Понимание внутреннего устройства основных коллекций
-->

## Пара слов о коллекции MultiKeyMap из Apache Commons

Ссылка на документацию: [`org.apache.commons.collections4.map.MultiKeyMap<K,V>`](https://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/map/MultiKeyMap.html).

Это реализация ассоциативного массива, где в качестве ключа используется [`MultiKey<K>`](https://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/keyvalue/MultiKey.html) (фактически массив ключей одного типа).

<!--
[`java.util.Map<K,V>`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Map.html)
-->

`MultiKeyMap` реализует интерфейсы:

- `Map<MultiKey<? extends K>,V>`
- `Get<MultiKey<? extends K>,V>`
- `IterableGet<MultiKey<? extends K>,V>`
- `IterableMap<MultiKey<? extends K>,V>`
- `Put<MultiKey<? extends K>,V>`

Наследует абстрактные классы:

- class `AbstractMapDecorator<MultiKey<? extends K>,V>`
    + class `AbstractIterableMap<K,V>`
        * interface `IterableMap<K,V>`
            - interface `Map<K,V>`
            - interface `Put<K,V>`
            - interface `IterableGet<K,V>`

## `MyMultiKeyMap`

Разработан аналог `MultiKeyMap`, с некоторыми ограничениями:

- отдельные методы, принимающие ключи по отдельности, сделаны лишь для _двух_ ключей (в `MultiKeyMap` написаны методы вплоть до 5),
- реализован лишь на основе собственной хеш-таблицы `MyHashMap` (`MultiKeyMap` работает также на базе `LinkedMap`, `LRUMap`, `ReferenceMap`)

## Успешное выполнение тестов

```
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running ru.spbstu.telematics.java.MyHashMapTest
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.577 s - in ru.spbstu.telematics.java.MyHashMapTest
[INFO] Running ru.spbstu.telematics.java.MyMultiKeyMapTest
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.035 s - in ru.spbstu.telematics.java.MyMultiKeyMapTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 15, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  5.334 s
[INFO] Finished at: 2023-12-20T21:35:41+03:00
[INFO] ------------------------------------------------------------------------
```