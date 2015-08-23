Example of how to use the [msgpack serialization library](http://msgpack.org/) to transfer matrices between Scala and Python, both compressed and uncompressed. Why?

Run `sbt generate` to get two output msgpack files, one compressed and one uncompressed. 
Install msgpack for python with `pip install u-msgpack-python` (note this isn't the canonical python binding, but I don't think there's much difference)
Load the data into python by running the testload.py script
