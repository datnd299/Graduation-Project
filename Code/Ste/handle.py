import numpy as np
import cv2
import matplotlib.pyplot as plt
from PIL import Image

img = cv2.imread('Image-dat9725.png') 
rows,cols,channel = img.shape
byteArr = bytearray()

print(img[0,0])
b,g,r = img[0,0]
sR = (r<<6)&255
sG = (g<<6)&255
sB = (b<<6)&255
print(sB)
byteArr.append(sR)
byteArr.append(sG)
byteArr.append(sB)
print(byteArr)


# for i in range(rows):
#     for j in range(cols):
#         b,g,r = img[i,j]




# im = Image.open('Image-dat9725.png', 'r')
# width, height = im.size
# pixel_values = list(im.getdata())
