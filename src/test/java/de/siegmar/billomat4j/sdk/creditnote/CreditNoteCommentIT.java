/*
 * Copyright 2012 Oliver Siegmar
 *
 * This file is part of Billomat4J.
 *
 * Billomat4J is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Billomat4J is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Billomat4J.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.siegmar.billomat4j.sdk.creditnote;

import de.siegmar.billomat4j.sdk.AbstractCommentIT;
import de.siegmar.billomat4j.sdk.domain.client.Client;
import de.siegmar.billomat4j.sdk.domain.creditnote.CreditNote;
import de.siegmar.billomat4j.sdk.domain.creditnote.CreditNoteActionKey;
import de.siegmar.billomat4j.sdk.domain.creditnote.CreditNoteComment;
import de.siegmar.billomat4j.sdk.domain.creditnote.CreditNoteCommentFilter;

public class CreditNoteCommentIT
    extends AbstractCommentIT<CreditNoteActionKey, CreditNoteComment, CreditNoteCommentFilter> {

    public CreditNoteCommentIT() {
        setService(creditNoteService);
    }

    @Override
    protected int createOwner() {
        final Client client = new Client();
        client.setName("CreditNoteCommentTest Client");
        clientService.createClient(client);

        final CreditNote creditNote = new CreditNote();
        creditNote.setClientId(client.getId());
        creditNoteService.createCreditNote(creditNote);

        return creditNote.getId();
    }

    @Override
    protected void deleteOwner(final int ownerId) {
        final int clientId = creditNoteService.getCreditNoteById(ownerId).getClientId();
        creditNoteService.deleteCreditNote(ownerId);
        clientService.deleteClient(clientId);
    }

    @Override
    protected CreditNoteComment buildComment(final int ownerId) {
        final CreditNoteComment comment = new CreditNoteComment();
        comment.setCreditNoteId(ownerId);
        return comment;
    }

    @Override
    protected CreditNoteActionKey buildActionKey() {
        return CreditNoteActionKey.CREATE;
    }

    @Override
    protected CreditNoteCommentFilter buildFilter() {
        return new CreditNoteCommentFilter().byActionKeys(CreditNoteActionKey.CANCEL);
    }

}