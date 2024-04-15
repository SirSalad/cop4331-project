package edu.ucf.cop4331project.container

/**
 * Marker annotation to auto register routers
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RouteMapping

/**
 * Marker interface for routing containers
 */
interface Router {

    val routeMapping: String
        get() = "/"
}