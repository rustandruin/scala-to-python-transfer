import umsgpack
import numpy as np
import gzip

f = open("testout.msgpack","r")
res = umsgpack.unpack(f)
f.close()

numeofs = res[0]
numobs = res[1]
numgridpts = res[2]

# breeze's toDenseVector method converts matrices in fortran/column-major order, so specify this explicitly since numpy's default is C/row-major order
U = np.array(res[3]).reshape((numgridpts, numeofs), order='F')
V = np.array(res[4]).reshape((numeofs, numobs), order='F')
S = np.array(res[5])
mean = np.array(res[6])


f = gzip.open("testout.msgpack.gz", "r")
res = umsgpack.unpack(f)
f.close()

gnumeofs = res[0]
gnumobs = res[1]
gnumgridpts = res[2]

gU = np.array(res[3]).reshape((numgridpts, numeofs), order='F')
gV = np.array(res[4]).reshape((numeofs, numobs), order='F')
gS = np.array(res[5])
gmean = np.array(res[6])

print np.linalg.norm(U -gU)


