package com.yuhtin.quotes.tasks.sql.provider.document.parser;


import com.yuhtin.quotes.tasks.sql.provider.document.Document;

public interface DocumentParser<T> {

    T parse(Document document);

}
