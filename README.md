Solid
=====

Solid is an Android library for data handling.

It provides:

* `SolidList` - an immutable parcelable `List`.

* `SolidMultimap` - an immutable parcelable multimap.

* Lightweight and composable data **streams**.

* Primitive array / Wrapped array **converters**.

* Data analysis **algorithms** (experimental).

*Under development* - file an issue and there is a big probability that I will add more features.
Just describe what you want with a real use case. I do not promise that I will implement
*anything* you wish, but if your need fits into the library nicely, I will add it for sure.

# `SolidList` and `SolidMultimap`

If you're a big fan of immutable data structures like me then you also probably miss `Parcelable` interface
implementation in *Guava*'s `ImmutableList`.

If you're not a big fan of immutability then you should be.

I recommend reading this library description: [AutoValue](https://github.com/google/auto/tree/master/value).
It has a very good Android port with `Parcelable` implementation: [AutoParcel](https://github.com/frankiesardo/auto-parcel).

There is also a library that makes a good combo with `SolidList` - [Icepick](https://github.com/frankiesardo/icepick).

*AutoParcel* + *Icepick* + `SolidList` rock. `SolidList` can be safely passed between activities / classes / intents
and threads, and it can be automatically saved into an activity/fragment `Bundle` with just one annotation. Amazing.

My typical usage for `SolidList` is to pass around a list of objects that has been created with *AutoParcel*.

## `SolidList` and `SolidMultimap` - details

Usage of `SolidList` is mostly identical to `ArrayList`, so I do not think that any docs are needed.
[SolidList](https://github.com/konmik/solid/blob/master/solid/src/main/java/solid/collections/SolidList.java)

`SolidMultimap` is a shortcut for `SolidList<Pair<K, SolidList<V>>>` with a set of handy construction methods.
[SolidMultimap](https://github.com/konmik/solid/blob/master/solid/src/main/java/solid/collections/SolidMultimap.java)

# Lightweight data streams

These streams are inspired by [RxJava](https://github.com/ReactiveX/RxJava), but
are much faster and take less memory. Unfortunately, we don't have high-performance Java 8 data streams on Android,
so here is an alternative. My tests say that this implementation is about 7 times faster than *RxJava*.

These (*Iterable*) streams are passive and do not emit items, so they are greatly simplified.
If you're stuck with studying *RxJava* you may try to understand these iterable streams first.

You can take any `Iterable` or an array and turn it into a set of chained methods
(all examples are with IntelliJ IDEA code folding):

``` java
stream(Arrays.asList(1, 2, 3))                    // Iterable<Integer>
    .filter((it) -> {return it < 3})              // only 1 and 2 items are not filtered
    .map((it) -> {return Integer.toString(it)}    // convert Integer values to String values
    .toList();
```

This code will result in a `List<String>` which contains `"1"` and `"2"` values.

Here is another example. We need to sort some items by name and then return their ids:

``` java
stream(namedEntities)
    .sort((left, right) -> {return left.name.compareTo(right.name);})
    .map((it) -> {return it.id;})
    .toSolidList();
```

Easy, isn't it? I believe you already know about power of streaming operators,
so here is a very lightweight and convenient implementation, especially for needs of an Android developer.

Here is another tip: any stream in this library implements `Iterable` itself, so you can put it in a temporary variable and
use it in a `for` statement later. Streams mostly are calculated lazily, so you can save some CPU cycles and memory
using them directly, without converting to a `List` first.

For a list of stream operators see: [Stream.java](https://github.com/konmik/solid/blob/master/solid/src/main/java/solid/stream/Stream.java)
This list is not so big right now but, and as I already said, file an issue! :) I don't want to bloat this library with methods
that will not be used by anyone, this is why I need your help.

## Non-streaming usage

All operators in the library can be used without streams.

In example, you can merge two arrays and use them in one `for` statement:

``` java
for (int value : new Merge<>(Arrays.asList(1, 2, 3), Arrays.asList(4, 6, 6)))
```

# Data converters and primitive arrays

Converters are inspired by Java 8 data streams. Here is how they look like:

``` java
int[] values = stream(Arrays.asList(1, 2, 3))   // Iterable<Integer> at this point
    .collect(toPrimitiveIntegerArray())
```

You can write your own converters if you wish - just implement a converting function and pass it to `Stream.convert`.

To convert a primitive array into an iterable stream just call one method.
Call two methods to convert them into an immutable parcelable list.

``` java
SolidList<Byte> list = Bytes.bytes(new byte[]{1, 2, 3})     // Iterable<Byte>
    .toSolidList();
```

Easy.

Want to convert them back?

``` java
byte[] values = stream(list)            // Iterable<Byte>
    .convert(toPrimitiveByteArray());
```

A piece of cake.

Want to join two primitive arrays?

``` java
byte[] joined = Bytes.bytes(new byte[]{1, 2, 3})
    .merge(Bytes.bytes(new byte[]{4, 5, 6}))
    .collect(toPrimitiveByteArray());
```

Remove a value from a primitive array?

``` java
byte[] array_1_3 = Bytes.bytes(new byte[]{1, 2, 3})
    .without((byte)2)
    .collect(toPrimitiveByteArray());
```

And so on. The amount of flexibility that iterable streams provide is hard to get at the
beginning but as long as you use them, more and more ideas come into mind.

# Data analysis (experimental)

Currently there is only one algorithm in two variations that is present in the library.
Probably I will find another place for such things.

One of the most annoying problems for me was a convenient way to compare a list of items
with a new list of items that has been changed by a user.

So, `StreamComparison` class provides a way to compare two `Iterable`s (lists, collections, iterable streams, etc)
and split the result into three parts: items that has been found in both lists, items that are only in the old list
and a stream of items that are only in the second list.

``` java
StreamComparison<Integer> comparison = new StreamComparison<>(Arrays.asList(1, 2, 3), Arrays.asList(3, 4, 5));
comparison.both(); // 3
comparison.first(); // 1, 2
comparison.second(); // 4, 5
```

And there is one tiny utility class that joins results into one iterable stream:

``` java
for (int i : new ListUpdate(comparison))
    // 3, 4, 5
```

There is also a version of this algorithm that allows to compare two streams of different data types,
in example if you have a database entity that corresponds to a network entity that has been received from a server,
and you need to compare two lists to update your database.

