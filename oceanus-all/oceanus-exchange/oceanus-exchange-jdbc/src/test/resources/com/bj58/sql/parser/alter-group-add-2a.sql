ALTER TABLE t1
    ADD GROUPING FOREIGN KEY ( c1, c2 )
        REFERENCES t2 (a1, a2)