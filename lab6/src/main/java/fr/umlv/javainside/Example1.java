package fr.umlv.javainside;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Example1 {
    public static void main(String[] args) {
//        final Object lock = new Object();
//        final ReentrantLock reentrantLock = new ReentrantLock();
//
//        var scope = new ContinuationScope("hello1");
//        var continuation = new Continuation(scope, () -> {
//            reentrantLock.lock();
//            try {
//                Continuation.yield(scope);
//            } finally {
//                reentrantLock.unlock();
//            }
//            System.out.println("hello continuation");
//        });
//        continuation.run();
//        continuation.run();

        var scope = new ContinuationScope("scope");
        var continuation1 = new Continuation(scope, () -> {
            System.out.println("start 1");
            Continuation.yield(scope);
            System.out.println("middle 1");
            Continuation.yield(scope);
            System.out.println("end 1");
        });
        var continuation2 = new Continuation(scope, () -> {
            System.out.println("start 2");
            Continuation.yield(scope);
            System.out.println("middle 2");
            Continuation.yield(scope);
            System.out.println("end 2");
        });
        var list = List.of(continuation1, continuation2);
        while (!continuation1.isDone() && !continuation2.isDone()) {
            list.get(0).run();
            list.get(1).run();
        }
    }
}

//3. En ajoutant yield avant le sysout il ne se passe plus rien
//Mais en ajoutant un autre run nous avons de nouveau l'affichage

//4. run --> lance la continuation
//yield --> sort de la continuation et reprend à partir du run
//run --> relance la continuation et reprend à partir du yield

//5. IllegalStateException

//6. Continuation dans le main --> null
//Thread --> la meme dans main ou dans la continuation

//7. synchronized --> IllegalStateException
// ReentrantLock --> ça marche!! :D