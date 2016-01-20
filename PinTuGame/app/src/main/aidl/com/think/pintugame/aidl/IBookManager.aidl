package com.think.pintugame.aidl;

import com.think.pintugame.aidl.Book;

interface IBookManager {
     List<Book> getBookList();
     void addBook(in Book book);
}