# kProperties

A kotlin multi platform library, that provides JavaFx like properties, bindings and observable collections.

## How to get it

The library is currently available under the following repository:

URL: 

    https://dl.bintray.com/kotlinberlin/releases

MAVEN: 

    <repository>
        <id>bintray.kotlinBerlin.releases</id>
        <url>https://dl.bintray.com/kotlinberlin/releases</url>
    </repository>
    
GRADLE: 

    maven {
        url = uri("https://dl.bintray.com/kotlinberlin/releases")
    }

Artifacts for the following platforms are available:

Platform | Artifact
------------ | -------------
multi platform | de.kotlin-berlin:kProperties:1.0-FINAL
jvm | de.kotlin-berlin:kProperties-jvm:1.0-FINAL
js | de.kotlin-berlin:kProperties-js:1.0-FINAL
mingwX64 | de.kotlin-berlin:kProperties-mingwX64:1.0-FINAL
mingwX86 | de.kotlin-berlin:kProperties-mingwX86:1.0-FINAL
androidNativeArm32 | de.kotlin-berlin:kProperties-androidNativeArm32:1.0-FINAL
androidNativeArm64 | de.kotlin-berlin:kProperties-androidNativeArm64:1.0-FINAL
wasm32 | de.kotlin-berlin:kProperties-wasm32:1.0-FINAL
linuxArm64 | de.kotlin-berlin:kProperties-linuxArm64:1.0-FINAL
linuxArm32Hfp | de.kotlin-berlin:kProperties-linuxArm32Hfp:1.0-FINAL
linuxX64 | de.kotlin-berlin:kProperties-linuxX64:1.0-FINAL

Supported but not available on maven central:

* watchosArm32
* watchosArm64
* watchosX86
* iosArm32
* iosArm64
* iosX64
* tvosArm64
* tvosX64
* macosX64
* linuxMips32
* linuxMipsel32

That is due to the fact that I am currently unable to build those targets from my machine. Feel free to clone
the repository yourself and build it for the platforms you need it. To build it just run gradle build in the cloned directory, and the project will be build for all possible platforms.

## How to use it

For a detailed overview have a look at the wiki pages that you can find [here](https://github.com/KotlinBerlin/kProperties/wiki) or at the api documentations that you can find [here](https://kotlinberlin.github.io/kProperties/index.html).

As a fast overview, here are the most important interfaces and their usage:

### [KObservable](https://kotlinberlin.github.io/kProperties/de.kotlin-berlin.k-properties/-k-observable/index.html)
This is the interface that all the other interfaces mentioned here implement themselves. It allows observers to react to
invalidation's of its content through the [KInvalidationListener](https://kotlinberlin.github.io/kProperties/de.kotlin-berlin.k-properties/-k-invalidation-listener/index.html) interface.

### [KObservableValue](https://kotlinberlin.github.io/kProperties/de.kotlin-berlin.k-properties.value/-k-observable-value/index.html)
A `KObservableValue` allows to observe the value wrapped by it for changes through the [KChangeListener](https://kotlinberlin.github.io/kProperties/de.kotlin-berlin.k-properties.value/-k-change-listener/index.html) interface.
It does not allow the external modification of its value.

### [KWritableValue](https://kotlinberlin.github.io/kProperties/de.kotlin-berlin.k-properties.value/-k-writable-value/index.html)
A `KWriableValue` implements the `KObservableValue` interface and adds the ability to modify the value externally.

### [KBinding](https://kotlinberlin.github.io/kProperties/de.kotlin-berlin.k-properties.binding/-k-binding/index.html)
A `KBinding` can calculate its value that may depend on one or more sources. If the sources implement the `KObservable` interface themselves then the binding may observe them and update its value whenever any source changes.
The `KBinding` itself implements the `KObservableValue` interface.

### [KReadOnlyProperty](https://kotlinberlin.github.io/kProperties/de.kotlin-berlin.k-properties.property/-k-read-only-property/index.html)
A property exposes information about the context it is used in. It provides access to its owner and its name and acts as a normal `KObservableValue`.

### [KProperty](https://kotlinberlin.github.io/kProperties/de.kotlin-berlin.k-properties.property/-k-property/index.html)
The `KProperty` interface combines the `KObservableValue`, `KWritableValue` and `KReadOnlyProperty` interfaces.

### [KWrapper](https://kotlinberlin.github.io/kProperties/de.kotlin-berlin.k-properties.wrapper/-k-wrapper/index.html)
A `KWrapper` should be used whenever internally a `KProperty` is required, but externally the value should not be modified. Therefore, the `KWrapper` interfaces adds a possibility to pass an `KReadOnlyProperty` to external components.

### [KObservableCollection](https://kotlinberlin.github.io/kProperties/de.kotlin-berlin.k-properties.collection/-k-observable-collection/index.html)
A collection that can be observed for added and removed elements through the [KCollectionListener](https://kotlinberlin.github.io/kProperties/de.kotlin-berlin.k-properties.collection/-k-collection-listener/index.html) interface.

### [KObservableList](https://kotlinberlin.github.io/kProperties/de.kotlin-berlin.k-properties.collection/-k-observable-list/index.html)
A list that can be observed for added and removed elements and for replacement and movements of elements inside the list through the [KListListener](https://kotlinberlin.github.io/kProperties/de.kotlin-berlin.k-properties.collection/-k-list-listener/index.html) interface. It also supports the `KCollectionListener` interface.

### [KObservableSet](https://kotlinberlin.github.io/kProperties/de.kotlin-berlin.k-properties.collection/-k-observable-set/index.html)
A set that can be observed for added and removed elements through the [KSetListener](https://kotlinberlin.github.io/kProperties/de.kotlin-berlin.k-properties.collection/-k-set-listener/index.html) interface. It also supports the `KCollectionListener` interface.

### [KObservableMap](https://kotlinberlin.github.io/kProperties/de.kotlin-berlin.k-properties.collection/-k-observable-map/index.html)
A map that can be observed for added and removed key value mappings and for replacements of values inside the list through the [KMapListener](https://kotlinberlin.github.io/kProperties/de.kotlin-berlin.k-properties.collection/-k-map-listener/index.html) interface.

## Open features
There are some open features that I would like to implement in the future, but those are not critical to the library.
Here is a list of the planned features:

* Make the list returned via the `KObservableList.sublist()` method observable itself let it fire change events when the sublist is modified.
* There already is the possibility to map a `KObservableValue` to any other value. For example a `KObservableValue` that holds a string can be mapped to a `KObservableValue` that holds the length of the first value. Currently, you can chain these mappings but for all of them a new `KBinding` instance is created which is not really efficient. 
Therefore, I want to implement a select builder that allows chaining of such mappings while only creating a single `KBinding` instance.

If there are any other features you are missing please feel free to submit an issue or write an email to info@kotlin-berlin.de, and I will try to implement it. Or implement it yourself and make a pull request.
