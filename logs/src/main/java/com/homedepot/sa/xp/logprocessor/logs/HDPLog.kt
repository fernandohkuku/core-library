package com.homedepot.sa.xp.logprocessor.logs

import android.os.Build
import android.util.Log
import java.io.PrintWriter
import java.io.StringWriter
import java.util.Collections
import java.util.Collections.unmodifiableList
import java.util.regex.Pattern

class HDPLog private constructor() {
    init {
        throw AssertionError()
    }

    abstract class Tree {
        internal val explicitTag = ThreadLocal<String>()

        internal open val tag: String?
            get() {
                val tag = explicitTag.get()
                if (tag != null) {
                    explicitTag.remove()
                }
                return tag
            }

        open fun v(message: String?, vararg args: Any?) {
            prepareLog(Log.VERBOSE, null, message, *args)
        }

        open fun v(t: Throwable?, message: String?, vararg args: Any?) {
            prepareLog(Log.VERBOSE, t, message, *args)
        }

        open fun v(t: Throwable?) {
            prepareLog(Log.VERBOSE, t, null)
        }

        open fun d(message: String?, vararg args: Any?) {
            prepareLog(Log.DEBUG, null, message, *args)
        }

        open fun d(t: Throwable?, message: String?, vararg args: Any?) {
            prepareLog(Log.DEBUG, t, message, *args)
        }

        open fun d(t: Throwable?) {
            prepareLog(Log.DEBUG, t, null)
        }

        open fun i(message: String?, vararg args: Any?) {
            prepareLog(Log.INFO, null, message, *args)
        }

        open fun i(t: Throwable?, message: String?, vararg args: Any?) {
            prepareLog(Log.INFO, t, message, *args)
        }

        open fun i(t: Throwable?) {
            prepareLog(Log.INFO, t, null)
        }

        open fun w(message: String?, vararg args: Any?) {
            prepareLog(Log.WARN, null, message, *args)
        }

        open fun w(t: Throwable?, message: String?, vararg args: Any?) {
            prepareLog(Log.WARN, t, message, *args)
        }

        open fun w(t: Throwable?) {
            prepareLog(Log.WARN, t, null)
        }

        open fun e(message: String?, vararg args: Any?) {
            prepareLog(Log.ERROR, null, message, *args)
        }

        open fun e(t: Throwable?, message: String?, vararg args: Any?) {
            prepareLog(Log.ERROR, t, message, *args)
        }

        open fun e(t: Throwable?) {
            prepareLog(Log.ERROR, t, null)
        }

        open fun wtf(message: String?, vararg args: Any?) {
            prepareLog(Log.ASSERT, null, message, *args)
        }

        open fun wtf(t: Throwable?, message: String?, vararg args: Any?) {
            prepareLog(Log.ASSERT, t, message, *args)
        }

        open fun wtf(t: Throwable?) {
            prepareLog(Log.ASSERT, t, null)
        }

        open fun log(priority: Int, message: String?, vararg args: Any?) {
            prepareLog(priority, null, message, *args)
        }

        open fun log(priority: Int, t: Throwable?, message: String?, vararg args: Any?) {
            prepareLog(priority, t, message, *args)
        }

        open fun log(priority: Int, t: Throwable?) {
            prepareLog(priority, t, null)
        }

        protected open fun isLoggable(priority: Int) = true
        protected open fun isLoggable(tag: String?, priority: Int) = isLoggable(priority)

        private fun prepareLog(priority: Int, t: Throwable?, message: String?, vararg args: Any?) {
            val tag = tag
            if (!isLoggable(tag, priority)) {
                return
            }

            var formattedMessage = message
            if (formattedMessage.isNullOrEmpty()) {
                if (t == null) {
                    return
                }
                formattedMessage = getStackTraceString(t)
            } else {
                if (args.isNotEmpty()) {
                    formattedMessage = formatMessage(formattedMessage, args)
                }
                if (t != null) {
                    formattedMessage += "\n" + getStackTraceString(t)
                }
            }

            log(priority, tag, formattedMessage, t)
        }

        protected open fun formatMessage(message: String, args: Array<out Any?>) =
            message.format(*args)

        private fun getStackTraceString(t: Throwable): String {
            val sw = StringWriter(256)
            val pw = PrintWriter(sw, false)
            t.printStackTrace(pw)
            pw.flush()
            return sw.toString()
        }

        protected abstract fun log(priority: Int, tag: String?, message: String, t: Throwable?)
    }

    open class DebugTree : Tree() {
        private val fqcnIgnore = listOf(
            HDPLog::class.java.name,
            HDPLog.Forest::class.java.name,
            Tree::class.java.name,
            DebugTree::class.java.name
        )

        override val tag: String?
            get() = super.tag ?: Throwable().stackTrace
                .first { it.className !in fqcnIgnore }
                .let(::createStackElementTag)

        protected open fun createStackElementTag(element: StackTraceElement): String? {
            var tag = element.className.substringAfterLast('.')
            val m = ANONYMOUS_CLASS.matcher(tag)
            if (m.find()) {
                tag = m.replaceAll("")
            }
            return if (tag.length <= MAX_TAG_LENGTH || Build.VERSION.SDK_INT >= 26) {
                tag
            } else {
                tag.substring(0, MAX_TAG_LENGTH)
            }
        }

        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (message.length < MAX_LOG_LENGTH) {
                if (priority == Log.ASSERT) {
                    Log.wtf(tag, message)
                } else {
                    Log.println(priority, tag, message)
                }
                return
            }

            var i = 0
            val length = message.length
            while (i < length) {
                var newline = message.indexOf('\n', i)
                newline = if (newline != -1) newline else length
                do {
                    val end = Math.min(newline, i + MAX_LOG_LENGTH)
                    val part = message.substring(i, end)
                    if (priority == Log.ASSERT) {
                        Log.wtf(tag, part)
                    } else {
                        Log.println(priority, tag, part)
                    }
                    i = end
                } while (i < newline)
                i++
            }
        }

        companion object {
            private const val MAX_LOG_LENGTH = 4000
            private const val MAX_TAG_LENGTH = 23
            private val ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$")
        }
    }

    companion object Forest : Tree() {
        @JvmStatic
        override fun v(message: String?, vararg args: Any?) {
            treeArray.forEach { it.v(message, *args) }
        }

        @JvmStatic
        override fun v(t: Throwable?, message: String?, vararg args: Any?) {
            treeArray.forEach { it.v(t, message, *args) }
        }

        @JvmStatic
        override fun v(t: Throwable?) {
            treeArray.forEach { it.v(t) }
        }

        @JvmStatic
        override fun d(message: String?, vararg args: Any?) {
            treeArray.forEach { it.d(message, *args) }
        }

        @JvmStatic
        override fun d(t: Throwable?, message: String?, vararg args: Any?) {
            treeArray.forEach { it.d(t, message, *args) }
        }

        @JvmStatic
        override fun d(t: Throwable?) {
            treeArray.forEach { it.d(t) }
        }

        @JvmStatic
        override fun i(message: String?, vararg args: Any?) {
            treeArray.forEach { it.i(message, *args) }
        }

        @JvmStatic
        override fun i(t: Throwable?, message: String?, vararg args: Any?) {
            treeArray.forEach { it.i(t, message, *args) }
        }

        @JvmStatic
        override fun i(t: Throwable?) {
            treeArray.forEach { it.i(t) }
        }

        @JvmStatic
        override fun w(message: String?, vararg args: Any?) {
            treeArray.forEach { it.w(message, *args) }
        }

        @JvmStatic
        override fun w(t: Throwable?, message: String?, vararg args: Any?) {
            treeArray.forEach { it.w(t, message, *args) }
        }

        @JvmStatic
        override fun w(t: Throwable?) {
            treeArray.forEach { it.w(t) }
        }

        @JvmStatic
        override fun e(message: String?, vararg args: Any?) {
            treeArray.forEach { it.e(message, *args) }
        }

        @JvmStatic
        override fun e(t: Throwable?, message: String?, vararg args: Any?) {
            treeArray.forEach { it.e(t, message, *args) }
        }

        @JvmStatic
        override fun e(t: Throwable?) {
            treeArray.forEach { it.e(t) }
        }

        @JvmStatic
        override fun wtf(message: String?, vararg args: Any?) {
            treeArray.forEach { it.wtf(message, *args) }
        }

        @JvmStatic
        override fun wtf(t: Throwable?, message: String?, vararg args: Any?) {
            treeArray.forEach { it.wtf(t, message, *args) }
        }

        @JvmStatic
        override fun wtf(t: Throwable?) {
            treeArray.forEach { it.wtf(t) }
        }

        @JvmStatic
        override fun log(priority: Int, message: String?, vararg args: Any?) {
            treeArray.forEach { it.log(priority, message, *args) }
        }

        @JvmStatic
        override fun log(priority: Int, t: Throwable?, message: String?, vararg args: Any?) {
            treeArray.forEach { it.log(priority, t, message, *args) }
        }

        @JvmStatic
        override fun log(priority: Int, t: Throwable?) {
            treeArray.forEach { it.log(priority, t) }
        }

        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            throw AssertionError()
        }

        @JvmStatic
        open inline fun asTree(): Tree = this

        @JvmStatic
        fun tag(tag: String): Tree {
            for (tree in treeArray) {
                tree.explicitTag.set(tag)
            }
            return this
        }

        @JvmStatic
        fun plant(tree: Tree) {
            require(tree !== this) { "Cannot plant HDPLog into itself." }
            synchronized(trees) {
                trees.add(tree)
                treeArray = trees.toTypedArray()
            }
        }

        @JvmStatic
        fun plant(vararg trees: Tree) {
            for (tree in trees) {
                requireNotNull(tree) { "trees contained null" }
                require(tree !== this) { "Cannot plant HDPLog into itself." }
            }
            synchronized(this.trees) {
                Collections.addAll(this.trees, *trees)
                treeArray = this.trees.toTypedArray()
            }
        }

        @JvmStatic
        fun uproot(tree: Tree) {
            synchronized(trees) {
                require(trees.remove(tree)) { "Cannot uproot tree which is not planted: $tree" }
                treeArray = trees.toTypedArray()
            }
        }

        @JvmStatic
        fun uprootAll() {
            synchronized(trees) {
                trees.clear()
                treeArray = emptyArray()
            }
        }

        @JvmStatic
        fun forest(): List<Tree> {
            synchronized(trees) {
                return unmodifiableList(trees.toList())
            }
        }

        @get:[JvmStatic JvmName("treeCount")]
        val treeCount get() = treeArray.size

        private val trees = ArrayList<Tree>()
        @Volatile private var treeArray = emptyArray<Tree>()
    }
}