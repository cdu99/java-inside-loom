package fr.umlv.javainside;

import java.util.concurrent.locks.ReentrantLock;

public class Example1 {
    public static void main(String[] args) {
        final Object lock = new Object();
        final ReentrantLock reentrantLock = new ReentrantLock();

        var scope = new ContinuationScope("hello1");
        var continuation = new Continuation(scope, () -> {
            reentrantLock.lock();
            try {
                Continuation.yield(scope);
            } finally {
                reentrantLock.unlock();
            }
            System.out.println("hello continuation");
        });
        continuation.run();
        continuation.run();
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