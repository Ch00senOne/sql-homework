CREATE TABLE PERIODCAL_TO_SUBSCRIBER(
    subscriber_id int,
    publication_id int,
    FOREIGN KEY (subscriber_id) REFERENCES SUBSCRIBERS (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (publication_id) REFERENCES PERIODICAL (id) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (subscriber_id,publication_id)
)