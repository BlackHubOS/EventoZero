/**
 *
 * EventoZero - Advanced event factory and executor for Bukkit and Spigot.
 * Copyright © 2016 BlackHub OS and contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package br.com.blackhubos.eventozero.chat.interpreter.base;

import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

import br.com.blackhubos.eventozero.chat.interpreter.pattern.IPattern;
import br.com.blackhubos.eventozero.chat.interpreter.values.ValueTransformer;

public interface QuestionBase<Value_Type> extends Question<Value_Type> {

    String id();

    String question();

    IPattern<Value_Type> pattern();

    // Override Methods

    @Override
    default Question<Value_Type> expect(Predicate<Value_Type> expect) {
        setExpect(expect);
        return this;
    }

    @Override
    default Question<Value_Type> booleanResult(Function<Value_Type, BooleanResult> function) {
        setBooleanResult(function);
        return this;
    }

    @Override
    default Question<Value_Type> yes(Question ifYes) {
        setYes((QuestionBase) ifYes);
        return this;
    }

    @Override
    default Question<Value_Type> no(Question ifNo) {
        setNo((QuestionBase) ifNo);
        return this;
    }

    @Override
    default Question<Value_Type> yes(BiConsumer<Player, Value_Type> ifYesConsumer) {
        setYes(ifYesConsumer);
        return this;
    }

    @Override
    default Question<Value_Type> no(BiConsumer<Player, Value_Type> ifNoConsumer) {
        setNo(ifNoConsumer);
        return this;
    }

    @Override
    default Question<Value_Type> yes(Question ifYes, BiConsumer<Player, Value_Type> ifYesConsumer) {
        setYes((QuestionBase) ifYes, ifYesConsumer);
        return this;
    }

    @Override
    default Question<Value_Type> no(Question ifYes, BiConsumer<Player, Value_Type> ifNoConsumer) {
        setNo((QuestionBase) ifYes, ifNoConsumer);
        return this;
    }


    // Set methods

    void setBooleanResult(Function<Value_Type, BooleanResult> function);

    void setYes(QuestionBase questionBase);

    void setNo(QuestionBase questionBase);

    void setYes(BiConsumer<Player, Value_Type> ifYesConsumer);

    void setNo(BiConsumer<Player, Value_Type> ifNoConsumer);

    void setYes(QuestionBase ifYes, BiConsumer<Player, Value_Type> ifYesConsumer);

    void setNo(QuestionBase ifNo, BiConsumer<Player, Value_Type> ifNoConsumer);

    Optional<QuestionBase> getQuestion(boolean approved);

    // Get methods

    Optional<BiConsumer<Player, Value_Type>> getConsumer(boolean approved);

    Optional<Function<Value_Type, BooleanResult>> getBooleanResult();

    Interpreter getInterpreter();

    Optional<Predicate<Value_Type>> getExpect();

    void setExpect(Predicate<Value_Type> expect);


    // Métodos funcionais, somente implemente caso saiba o que está fazendo

    default ValueTransformer<Value_Type> transformer() {
        return pattern().getTransformer();
    }

    default boolean approve(String input) {
        return !getExpect().isPresent() || getExpect().get().test(transform(input));
    }

    default Value_Type transform(String input) {
        return transformer().transform(input);
    }

    default boolean isOk(String input) {
        return pattern().check(input) && approve(input);
    }

    default Optional<QuestionBase> interpreterNext(Player player) {
        return getInterpreter().deque(player);
    }

    default boolean remove(Player player, QuestionBase question) {
        return getInterpreter().remove(player, question);
    }

    default boolean hasNext(Player player) {
        return getInterpreter().hasNext(player);
    }

    default Optional<QuestionBase> next(Player player, String inputApply) {
        Value_Type value = transform(inputApply);

        boolean state = true; //state always = yes

        Optional<Function<Value_Type, BooleanResult>> yesOrNo = getBooleanResult();
        if (yesOrNo.isPresent()) {
            state = yesOrNo.get().apply(value) == BooleanResult.YES;
        } else {
            state = pattern().yesOrNo(value);
        }

        Optional<QuestionBase> question = getQuestion(state);
        Optional<BiConsumer<Player, Value_Type>> consumer = getConsumer(state);
        consumer.ifPresent(playerConsumer -> playerConsumer.accept(player, value));

        if (question.isPresent()) {
            remove(player, question.get());
            getInterpreter().setCurrent(player, question);
            return question;
        } else {
            if (!hasNext(player)) {
                getInterpreter().end(player);
                return Optional.empty();
            } else {
                return interpreterNext(player);
            }
        }
    }

}