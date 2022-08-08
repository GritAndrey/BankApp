package ru.gritandrey.bill.mapper;

public interface Mapper<F, T> {
    T mapFrom(F object);
}
