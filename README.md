# LogAnalyser

Write a program that analyses a web server log and produces basic statistics. Assume that the log file is
constantly growing (still being written to) while statistical operations are performed.
A sample line in the log file will look something like this -

1 180.76.15.24 - - [23/Feb/2016:08:25:04 +0400] "GET /product/JM00116469/nature-made-dha-90-
capsules-japanese-imported-supplements-1/259383 HTTP/1.1" 301 178 "-
" "Mozilla/5.0 (compatible; Baiduspider/2.0; +http://www.baidu.com/search/spider.html)"

The first field is the accessing IP address, the next is the date, the third the requested URL and method, the
next the returned HTTP code, the next the size of the request and finally the agent user string

Assuming a large (millions of lines) log file, writing the most memory efficient program possible to
perform the following computations:
The most accessed pages
The top 10 IP addresses providing the most traffic by number of hits
The top 10 IP addresses providing the most traffic by bandwidth
The top 20 user agents
Perform the same computations, but for a configurable (30, 60, 120 minute) window instead of for
the entire log file
