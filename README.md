Solid
=====

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Solid-green.svg?style=flat)](https://android-arsenal.com/details/1/1955)

Solid is an Android library for data handling.

It provides:

* `SolidList`, `SolidMap`, `SolidSet` - immutable, parcelable collections.

* Lightweight and composable **data streams**.

* Primitive array / wrapped array **converters**.

* 60Kb jar

### Philosophy

*Solid* library adheres to the philosophy: "transform it as a stream, keep it as immutable".
Thus allowing to pass collections around without a fear that they can be changed by another part
of the application, while keeping the ability to transform data in a convenient way.

### Include

``` groovy
dependencies {
    compile 'info.android15.solid:solid:1.0.13'
}
```

### Further development

File an issue and there is a big probability that I will add more features.
Just describe what you want with a real use case. I do not promise that I will implement
*anything* you wish, but if your need fits into the library nicely, I will add it for sure.

[Changelog](https://github.com/konmik/solid/blob/master/CHANGELOG.md)

# Solid collections

If you're a big fan of immutable data structures like me then you also probably miss `Parcelable` interface
implementation in *Guava*'s `ImmutableList`.

If you're not a big fan of immutability then you should be.

I recommend reading this library description to get started with immutability: [AutoValue](https://github.com/google/auto/tree/master/value).
The library has a very good Android port with `Parcelable` implementation: [AutoParcel](https://github.com/frankiesardo/auto-parcel).

There is yet another library that makes a good combo with `SolidList` - [Icepick](https://github.com/frankiesardo/icepick).
*Solid* collections can be safely passed between activities, services, intents
and threads, and it can be automatically saved into an activity/fragment `Bundle` with just one annotation. Amazing.

### Details

*Solid* collections are just a decorators around `ArrayList`, `LinkedHashMap` and `LinkedHashSet`,
so I do not think that any docs are needed.
`UnsupportedOperationException` become thrown on each method that tries to modify a *solid* collection.

If you're familiar with Guava's immutable collections - there is a difference that is good to know. *Solid* collections do
not have a support for *Builder* pattern - use `ArrayList`, `Stream`, `Map` and `Set` to prepare them.

# Lightweight data streams

*Solid* streams are similar to Java 8 streams but they are released under the MIT license and are absolutely free.

The usual approach on Android is to use RxJava for functional data handling, but it is too heavy for
a non-event based usage pattern. My tests say that *Solid* streams are about 5 times faster than RxJava and take about
5 times less memory the same time on small data sets.

These streams are passive and do not emit items, they are not thread-safe, so they are much simpler.
If you're stuck with studying *RxJava* you may try to understand these streams first.

### How to use

All examples here with Java 8 syntax on. I recommend using [Gradle Retrolambda Plugin](https://github.com/evant/gradle-retrolambda)
to make your code shorter and to bring the functional power to your code.

You can take any `Iterable` or an array and turn it into a set of chained methods:

``` java
stream(asList(1, 3, 2))                // Iterable<Integer>
    .filter(it -> it < 3)              // only 1 and 2 items are not filtered
    .map(it -> Integer.toString(it))   // convert Integer values to String values
    .toList();
```

This code will result in a `List<String>` which contains `"1"` and `"2"` values.

Another example: we need to sort some items by name and then return their ids in a `SolidList`.

``` java
stream(namedEntities)
    .sort((left, right) -> left.name.compareTo(right.name))
    .map(it -> it.id)
    .toSolidList();
```

Easy, isn't it? I believe you already know about the power of streaming operators,
so here is a very lightweight and convenient implementation, especially for needs of an Android developer.

Here is another tip: any stream in this library implements `Iterable` itself, so you can put it in a temporary variable and
use it in a `for` statement later. Streams mostly are calculated lazily, so you can save some CPU cycles and memory
using them directly, without converting to `List` first.

For a list of stream operators see: [Stream.java](https://github.com/konmik/solid/blob/master/solid/src/main/java/solid/stream/Stream.java)

### Non-streaming usage

All features of this library can be used without chaining operators.

For example, you can merge two arrays and use them in one `for` statement using `Merge` class that is internally used
to back `Stream.merge(...)` operator:

``` java
for (int value : new Merge<>(asList(1, 2, 3), asList(4, 5, 6)))
    ...
```

Despite of there are some debates on *what* is OOP, the possibility of using operators as objects is here.

# Data converters and primitive arrays

Here is how converters look like:

``` java
int[] values = stream(asList(1, 2, 3))   // Iterable<Integer> at this point
    .collect(toPrimitiveIntegerArray())  // int[]
```

Currently Solid supports conversion of these primitive types: `byte`, `double`, `float`, `int`, `long`.

You can write your own converters if you wish - just implement a converting function and pass it into `Stream.collect()`.

*Solid* converters are quite powerful:

To convert a primitive array into an iterable stream just call one method.
Call two methods to convert them into an immutable parcelable list.

``` java
SolidList<Byte> list = bytes(new byte[]{1, 2, 3})
    .toSolidList();
```

Easy.

Want to convert them back?
`SolidList` is a subclass of `Stream`, so you don't even have to write `stream(list)` here. ;)

``` java
byte[] values = list.collect(toPrimitiveByteArray());
```

A piece of cake.

Want to join two primitive arrays?

``` java
byte[] joined = bytes(new byte[]{1, 2, 3})
    .merge(bytes(new byte[]{4, 5, 6}))
    .collect(toPrimitiveByteArray());
```

Remove a value from a primitive array?

``` java
byte[] array_1_3 = bytes(new byte[]{1, 2, 3})
    .without((byte)2)
    .collect(toPrimitiveByteArray());
```

And so on. The amount of flexibility that iterable streams and converters provide is hard to get at the
beginning but as long as you use them, more and more ideas come into mind.

The full list of possible converters is here: [converters](https://github.com/konmik/solid/tree/master/solid/src/main/java/solid/converters)

