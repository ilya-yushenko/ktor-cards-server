package org.yushenko.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.yushenko.data.model.CardModel
import org.yushenko.data.model.UserModel
import org.yushenko.data.model.requests.CardRequest
import org.yushenko.data.model.response.BaseResponse
import org.yushenko.domain.usecase.CardUseCase
import org.yushenko.utils.Constants

fun Route.CardRoute(cardUseCase: CardUseCase) {

    authenticate("jwt") {

        get("api/v1/get-all-cards") {
            try {
                val cards = cardUseCase.getAllCards()
                call.respond(HttpStatusCode.OK, cards)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Constants.Error.GENERAL))
            }

        }

        post("api/v1/create-card") {
            val cardRequest = call.receiveNullable<CardRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.MISSING_FIELDS))
                return@post
            }

            try {
                val card = CardModel(
                    id = 0,
                    owner = call.principal<UserModel>()!!.id,
                    cardTitle = cardRequest.cardTitle,
                    cardDescription = cardRequest.cardDescription,
                    cardDate = cardRequest.cardDate,
                    isVerified = cardRequest.isVerified
                )

                cardUseCase.addCard(card = card)
                call.respond(HttpStatusCode.OK, BaseResponse(true, Constants.Success.CARD_ADDED_SUCCESSFULLY))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Constants.Error.GENERAL))
            }
        }

        post("api/v1/update-card") {
            val cardRequest = call.receiveNullable<CardRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.MISSING_FIELDS))
                return@post
            }

            try {
                val ownerId = call.principal<UserModel>()!!.id
                val card = CardModel(
                    id = cardRequest.id ?: 0,
                    owner = ownerId,
                    cardTitle = cardRequest.cardTitle,
                    cardDescription = cardRequest.cardDescription,
                    cardDate = cardRequest.cardDate,
                    isVerified = cardRequest.isVerified
                )

                cardUseCase.updateCard(card = card, ownerId = ownerId)
                call.respond(HttpStatusCode.OK, BaseResponse(true, Constants.Success.CARD_UPDATED_SUCCESSFULLY))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Constants.Error.GENERAL))
            }
        }

        delete("api/v1/delete-card") {
            val cardRequest = call.request.queryParameters[Constants.Value.ID]?.toInt() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.MISSING_FIELDS))
                return@delete
            }

            try {
                val ownerId = call.principal<UserModel>()!!.id

                cardUseCase.deleteCard(cardId = cardRequest, ownerId = ownerId)
                call.respond(HttpStatusCode.OK, BaseResponse(true, Constants.Success.CARD_DELETED_SUCCESSFULLY))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Constants.Error.GENERAL))
            }
        }
    }
}