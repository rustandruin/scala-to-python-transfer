Example of how to use the [msgpack serialization library](http://msgpack.org/) to transfer matrices between Scala and Python, both compressed and uncompressed. Why? I found myself using Scala-Spark to generate GB size matrices that I dumped from Java in binary format using writeDouble, and then having no way to load these in Python w/o annoying contortions (viz., converting to CSV first). This alternative requires less code and should be much faster, since there's no need for string processing.

Run `sbt generate` to get two output msgpack files, one compressed and one uncompressed. 
Install msgpack for python with `pip install u-msgpack-python` (note this isn't the canonical python binding, but I don't think there's much difference)
Load the data into python by running the testload.py script
