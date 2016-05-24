package controllers

import javax.inject._
import play.api._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._

@Singleton
class ApplicationController @Inject()(webJarAssets: WebJarAssets, messages: MessagesApi) extends Controller with I18nSupport {

  def index = Action {
    implicit request =>
      Ok(views.html.index(webJarAssets))
  }
  override def messagesApi: MessagesApi = messages

}
