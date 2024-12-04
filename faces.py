#from sklearn.datasets import fetch_olivetti_faces
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

#facesds = fetch_olivetti_faces()
#features = facesds.data
#target = facesds.target
#print(features.shape) #print(target.shape) #print(target)

#xdf = pd.DataFrame(features)
#xdf.to_csv('xdf_faces.csv')
#ydf = pd.DataFrame(target)
#ydf.to_csv('ydf_faces.csv')

x = pd.read_csv("xdf_faces.csv")
y = pd.read_csv("ydf_faces.csv")
x = x.to_numpy()
y = y.to_numpy()
y = y.reshape(-1,)
#print(x.shape) #print(y.shape) #print(y)

fig, subplots = plt.subplots(nrows=5, ncols=8, figsize=(14,8))
subplots =subplots.flatten()

for uid in np.unique(y):
    indx = uid*8

    subplots[uid].imshow(x[indx].reshape(64,64), cmap='grey')
    subplots[uid].set_xticks([])
    subplots[uid].set_yticks([])
plt.show()
