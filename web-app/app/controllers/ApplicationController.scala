package controllers

import javax.inject._
import play.api._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._

@Singleton
class ApplicationController @Inject()(injectedWebJars: WebJarAssets, messages: MessagesApi) extends Controller with I18nSupport {

  def index = Action {
    implicit request =>
      Ok(views.html.index())
  }

  implicit def webJarAssets: WebJarAssets = injectedWebJars

  override def messagesApi: MessagesApi = messages

}
