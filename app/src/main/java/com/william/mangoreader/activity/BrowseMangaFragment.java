package com.william.mangoreader.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.william.mangoreader.R;
import com.william.mangoreader.listener.InfiniteScrollListener;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.cards.actions.BaseSupplementalAction;
import it.gmariotti.cardslib.library.cards.actions.IconSupplementalAction;
import it.gmariotti.cardslib.library.cards.material.MaterialLargeImageCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardGridArrayAdapter;
import it.gmariotti.cardslib.library.view.CardGridView;

public class BrowseMangaFragment extends Fragment {

    private ArrayList<Card> cards;
    private CardGridView gridView;

    AppCompatActivity activity;


    public BrowseMangaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_browse_manga, container, false);

        final CardGridView gridView = (CardGridView) rootView.findViewById(R.id.browse_cards);

        // TODO: asynchronous loading
        cards = new ArrayList<Card>();
        createEntry(activity, cards);

        final CardGridArrayAdapter mCardArrayAdapter = new CardGridArrayAdapter(activity, cards);
        gridView.setAdapter(mCardArrayAdapter);

        gridView.setOnScrollListener(new InfiniteScrollListener(5) {
            @Override
            public void loadMore(int page, int totalItemsCount) {
                createEntry(activity, cards);
                // TODO: load from JSON
                mCardArrayAdapter.notifyDataSetChanged();
            }
        });

        setHasOptionsMenu(true);
        return rootView;
    }

    private void createEntry(final Context context, ArrayList<Card> cards) {

        // Set supplemental actions as icon
        ArrayList<BaseSupplementalAction> actions = new ArrayList<BaseSupplementalAction>();

        IconSupplementalAction t1 = new IconSupplementalAction(context, R.id.bookmark_but);
        t1.setOnActionClickListener(new BaseSupplementalAction.OnActionClickListener() {
            @Override
            public void onClick(Card card, View view) {
                System.out.println("Bookmark action clicked...");
                ImageButton bmButton = (ImageButton) view.findViewById(R.id.bookmark_but);
                bmButton.setSelected(!bmButton.isSelected());
                if (bmButton.isSelected()) {
                    //TODO: add to user library
                    Toast.makeText(context, "Bookmarked.", Toast.LENGTH_SHORT).show();
                } else {
                    //TODO: remove from user library
                    Toast.makeText(context, "Removed from library.", Toast.LENGTH_SHORT).show();

                }
            }
        });
        actions.add(t1);

        // setup card
        MaterialLargeImageCard card = MaterialLargeImageCard.with(context)
                .setTitle("Manga Title Here")
                .setSubTitle("Author Name")
                .useDrawableId(R.drawable.manga2)
                .setupSupplementalActions(R.layout.card_actions, actions)
                .build();

        card.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                // TODO: open detail on card click
                Toast.makeText(context, " Click on Card.", Toast.LENGTH_SHORT).show();
            }
        });

        cards.add(card);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuinflator) {
        menuinflator.inflate(R.menu.menu_browse_manga, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_search) {
            Toast.makeText(getActivity().getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
