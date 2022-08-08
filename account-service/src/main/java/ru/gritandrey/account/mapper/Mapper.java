package ru.gritandrey.account.mapper;

public interface Mapper<F, T> {
    T mapFrom(F object);
}
