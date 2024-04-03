// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    // @see
    /**
     * @see <a href="https://stackoverflow.com/a/77720024/3152877">Unable to load class 'org.jetbrains.kotlin.config.LanguageVersion'</a>
     */
    id("com.google.devtools.ksp") version "1.8.21-1.0.11" apply false

}