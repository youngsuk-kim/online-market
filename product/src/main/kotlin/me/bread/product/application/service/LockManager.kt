package me.bread.product.application.service

import java.util.concurrent.locks.Lock

interface LockManager {
    fun getLock(lockId: String): Lock?
    fun unlock(lock: Lock)
}
