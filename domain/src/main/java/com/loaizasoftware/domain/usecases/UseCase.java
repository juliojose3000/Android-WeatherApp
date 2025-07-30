package com.loaizasoftware.domain.usecases;

import java.util.concurrent.CompletableFuture;

public abstract class UseCase<A, B> {
    public abstract CompletableFuture<B> run(A params);
}
