USE `mazes`;
SELECT * FROM mazes.test INTO OUTFILE 'data.csv' FIELDS TERMINATED BY ',';