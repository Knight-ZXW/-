from collections import defaultdict
from operator import attrgetter

pieces = {
    'A':2.1,
    'B':1.0,
    'C':3.5
}
print(pieces)
min_prices = min(zip(pieces.values(),pieces.keys()))
print(min_prices)
sorted_pieces = sorted(zip(pieces.values(),pieces.keys()))
print(sorted_pieces)